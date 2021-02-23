package com.xut.controller.user;

import com.xut.bean.*;
import com.xut.controller.BaseController;
import com.xut.controller.auth.AuthUtil;
import com.xut.controller.data.OrderData;
import com.xut.filter.Identity;
import com.xut.model.*;
import com.xut.service.*;
import com.xut.util.TimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/order")
public class UserOrderController extends BaseController {
    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomOrderService roomOrderService;

    @GetMapping("/MyOrder")
    public ModelAndView myOrder(HttpServletRequest request) {
        return new ModelAndView("user/myOrder", getUserModel(request));
    }

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody OrderData orderData, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        Order order = orderData.getOrder();
        if (order.valid()) {
            boolean canUpdate = checkIfExistsRoom(order.getRoomTypeId(), order.getCheckInTime(), order.getCheckOutTime());
            if (!canUpdate) {
                result.setCode(Code.NO_ROOM_TO_ORDER);
                result.setMsg("房间已满");
                return result;
            }
            order.setUserId(identity.getUserId());
            Result<RoomType> roomTypeResult = roomTypeService.getById(order.getRoomTypeId());
            if (roomTypeResult.isValid()) {
                Double price = roomTypeResult.getData().getPrice();
                int betweenDays = TimeUtils.getBetweenDays(order.getCheckInTime(), order.getCheckOutTime());
                order.setPrice(price * betweenDays);
            }
            result = orderService.create(order);
            if (result.isNotOK()) {
                return result;
            }

            List<Client> clients = orderData.getClients();
            clients = clients.stream().map(item -> {
                item.setOrderId(order.getId());
                return item;
            }).collect(Collectors.toList());
            result = clientService.create(clients);
        }
        return result;
    }

    @GetMapping("/getOrdersByUser")
    public Result<List<OrderData>> getList(HttpServletRequest request) {
        Result<List<OrderData>> result = new Result<>();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        Integer userId = identity.getUserId();
        Result<Page<Order>> ordersResult = orderService.search(Collections.singletonList(userId), null, null, null, 1, Integer.MAX_VALUE);
        if (ordersResult.isNotValid()) {
            result.setCode(ordersResult.getCode());
            return result;
        }
        Page<Order> page = ordersResult.getData();
        List<Order> orders = page.getList();
        if (CollectionUtils.isEmpty(orders)) {
            return result;
        }
        List<Integer> ids = orders.stream().map(Order::getId).collect(Collectors.toList());
        Result<Map<Integer, List<Client>>> clientMapResult = clientService.getClientMap(ids);
        if (clientMapResult.isNotOK()) {
            result.setCode(clientMapResult.getCode());
            return result;
        }
        List<Integer> orderIds = orders.stream().map(Order::getRoomTypeId).collect(Collectors.toList());
        Result<Page<RoomType>> roomTypeResult = roomTypeService.get(1, Integer.MAX_VALUE);
        if (roomTypeResult.isNotValid()) {
            result.setCode(roomTypeResult.getCode());
            return result;
        }
        Map<Integer, RoomType> roomTypeMap =
                roomTypeResult.getData().getList().stream().collect(Collectors.toMap(RoomType::getId, item -> item));
        List<OrderData> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(new OrderData(roomTypeMap.get(order.getRoomTypeId()), order,
                    clientMapResult.getData().get(order.getId())));
        }

        result.setData(list);

        return result;
    }

    @PostMapping("/delete")
    public NoneDataResult delete(@RequestParam("id") Integer id) {
        NoneDataResult result = new NoneDataResult();
        Result<Page<Order>> orderResult = orderService.search(null, null, id, null, 1, 1);
        if (orderResult.isNotValid()) {
            result.setCode(orderResult.getCode());
            return result;
        }
        Order order = orderResult.getData().getList().get(0);
        if (order.getStatus() == OrderStatus.CANCELED.id()) {
            result = orderService.delete(id);
        }
        return result;
    }

    @PostMapping("/cancelOrder")
    public NoneDataResult cancelOrder(@RequestParam("id") Integer id) {
        NoneDataResult result = new NoneDataResult();
        Result<Page<Order>> orderResult = orderService.search(null, null, id, null, 1, 1);
        if (orderResult.isNotValid()) {
            result.setCode(orderResult.getCode());
            return result;
        }
        Order order = orderResult.getData().getList().get(0);
        order.setStatus(OrderStatus.CANCELED.id());
        result = orderService.update(order);
        return result;
    }

    @PostMapping("/updateOrder")
    public NoneDataResult updateOrder(@RequestBody OrderData orderData, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        Order order = orderData.getOrder();
        boolean canUpdate = checkIfExistsRoom(order.getRoomTypeId(), order.getCheckInTime(), order.getCheckOutTime());
        if (!canUpdate) {
            result.setCode(Code.NO_ROOM_TO_ORDER);
            result.setMsg("房间已满");
            return result;
        }
        result = orderService.update(order);
        if (result.isNotOK()) {
            return result;
        }
        List<Client> clients = orderData.getClients();
        if (CollectionUtils.isEmpty(clients)) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        result = clientService.delete(order.getId());
        if (result.isNotOK()) {
            return result;
        }
        clients = clients.stream().map(item -> {
            item.setOrderId(order.getId());
            return item;
        }).collect(Collectors.toList());
        result = clientService.create(clients);
        return result;
    }


    private boolean checkIfExistsRoom(Integer roomTypeId, Date checkInDate, Date checkOutDate) {
        if (roomTypeId == null || checkInDate == null || checkOutDate == null) {
            return false;
        }
        /**
         * 获取已入住和交易中的订单
         */
        List<Order> orders = orderService.search(roomTypeId, Arrays.asList(OrderStatus.IN_PROGRESS.id(), OrderStatus.HAS_CHECKED_IN.id()));
        Result<Page<RoomData>> roomResult = roomService.search(Collections.singletonList(roomTypeId), null, 1, Integer.MAX_VALUE);
        if (roomResult.isNotValid()) {
            return false;
        }
        List<RoomData> rooms = roomResult.getData().getList();
        /**
         * 获取当前时间段已下单的订单数
         */
        long count = orders.stream().filter((item) -> {
            return (item.getCheckInTime().getTime() >= checkInDate.getTime() && item.getCheckInTime().getTime() <= checkOutDate.getTime())
                    || (item.getCheckOutTime().getTime() >= checkInDate.getTime() && item.getCheckOutTime().getTime() <= checkOutDate.getTime());
        }).count();
        if (count < rooms.size()) {
            return true;
        }
        return false;
    }

}
