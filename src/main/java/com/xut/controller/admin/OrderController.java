package com.xut.controller.admin;

import com.xut.bean.*;
import com.xut.controller.BaseController;
import com.xut.controller.auth.AuthUtil;
import com.xut.controller.data.OrderData;
import com.xut.controller.data.RoomRequest;
import com.xut.filter.Identity;
import com.xut.model.*;
import com.xut.service.*;
import com.xut.util.TimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class OrderController extends BaseController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;
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


    @GetMapping("/order")
    public ModelAndView orderPage(HttpServletRequest request) {
        Map<String, Object> map = getUserModel(request);
        Result<List<RoomType>> roomTypesResult = roomTypeService.getList(1, Integer.MAX_VALUE);
        if (roomTypesResult.isValid()) {
            map.put("types", roomTypesResult.getData());
        }
        return new ModelAndView("/admin/orderMgr", map);
    }

    @GetMapping("/orderList")
    public Result<Page<OrderData>> getOrderList(@RequestParam(value = "typeId", required = false) Integer roomTypeId,
                                                @RequestParam(value = "status", required = false) Integer status,
                                                @RequestParam(value = "offset", required = false) Integer offset,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Result<Page<OrderData>> result = new Result<>();
        Result<Page<Order>> ordersResult = orderService.search(null, roomTypeId == null ? null : Collections.singletonList(roomTypeId), null, status, offset, pageSize);
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

        Result<List<RoomOrder>> roomOrderResult
                = roomOrderService.search(ids);
        Map<Integer, RoomData> roomDataMap = new HashMap<>();
        if (roomOrderResult.isValid()) {
            Set<Integer> set = roomOrderResult.getData().stream().map(RoomOrder::getRoomId).collect(Collectors.toSet());
            Result<List<RoomData>> roomsResult = roomService.getByIds(new ArrayList<>(set));
            if (roomsResult.isValid()) {
                Map<Integer, RoomData> dataByRoomIdMap =
                        roomsResult.getData().stream().collect(Collectors.toMap(RoomData::getId, item -> item));
                for (RoomOrder roomOrder : roomOrderResult.getData()) {
                    roomDataMap.put(roomOrder.getOrderId(), dataByRoomIdMap.get(roomOrder.getRoomId()));
                }
            }
        }


        Result<List<RoomType>> roomTypeResult = roomTypeService.getList(1, Integer.MAX_VALUE);
        if (roomTypeResult.isNotValid()) {
            result.setCode(roomTypeResult.getCode());
            return result;
        }
        Map<Integer, RoomType> roomTypeMap =
                roomTypeResult.getData().stream().collect(Collectors.toMap(RoomType::getId, item -> item));
        List<OrderData> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(new OrderData(roomTypeMap.get(order.getRoomTypeId()), order,
                    clientMapResult.getData().get(order.getId()), roomDataMap.get(order.getId())));
        }
        Page<OrderData> orderDataPage = new Page<>();
        orderDataPage.setTotalCount(page.getTotalCount());
        orderDataPage.setList(list);
        result.setData(orderDataPage);

        return result;
    }

    @PostMapping("/allocateRoom")
    public NoneDataResult allocateRoom(@RequestBody RoomOrder roomOrder) {
        NoneDataResult result = roomOrderService.create(roomOrder);
        if (result.isOK()) {
            Result<Page<Order>> pageResult = orderService.search(null, null, roomOrder.getOrderId(), null, 1, 1);
            if (pageResult.isValid() && CollectionUtils.isNotEmpty(pageResult.getData().getList())) {
                Order order = pageResult.getData().getList().get(0);
                if (new Date().getTime() >= Optional.ofNullable(order.getCheckInTime().getTime()).orElse((long) 0)) {
                    order.setStatus(OrderStatus.HAS_CHECKED_IN.id());
                }
                order.setRoomId(roomOrder.getRoomId());
                orderService.update(order);
            }
        }
        return result;
    }

    @PostMapping("/order/create")
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
            order.setUserId(0);
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

    @RequestMapping("/order/getRooms")
    public Result<List<RoomData>> getRooms(@RequestBody RoomRequest request) {
        Result<List<RoomData>> result = new Result<>();
        List<RoomData> roomDatas = getEnableRooms(request.getRoomTypeId(), request.getCheckInTime(), request.getCheckOutTime());
        result.setData(roomDatas);
        return result;
    }

    @PostMapping("/order/updateStatus")
    public NoneDataResult cancelOrder(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        NoneDataResult result = new NoneDataResult();
        Result<Page<Order>> orderResult = orderService.search(null, null, id, null, 1, 1);
        if (orderResult.isNotValid()) {
            result.setCode(orderResult.getCode());
            return result;
        }
        Order order = orderResult.getData().getList().get(0);
        order.setStatus(status);
        result = orderService.update(order);
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

    /**
     * 获取可入住的房间
     * @param roomTypeId
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    private List<RoomData> getEnableRooms(Integer roomTypeId, Date checkInDate, Date checkOutDate) {
        if (roomTypeId == null || checkInDate == null || checkOutDate == null) {
            return null;
        }
        List<RoomData> list = new ArrayList<>();
        List<Order> orders = orderService.search(roomTypeId, Arrays.asList(OrderStatus.IN_PROGRESS.id(), OrderStatus.HAS_CHECKED_IN.id()));
        Result<Page<RoomData>> roomResult = roomService.search(Collections.singletonList(roomTypeId), null, 1, Integer.MAX_VALUE);
        List<RoomData> rooms = roomResult.getData().getList();
        List<Order> inProgressOrders = orders.stream().filter((item) -> {
            return (item.getCheckInTime().getTime() >= checkInDate.getTime() && item.getCheckInTime().getTime() <= checkOutDate.getTime())
                    || (item.getCheckOutTime().getTime() >= checkInDate.getTime() && item.getCheckOutTime().getTime() <= checkOutDate.getTime());
        }).collect(Collectors.toList());
        List<Integer> orderIds = inProgressOrders.stream().map(Order::getId).collect(Collectors.toList());
        Result<List<RoomOrder>> roomOrderResult = roomOrderService.search(orderIds);
        if (roomOrderResult.isOK()) {
            List<RoomOrder> datas = roomOrderResult.getData();
            datas = datas == null ? new ArrayList<>() : datas;
            Map<Integer, RoomOrder> map = datas.stream().collect(Collectors.toMap(RoomOrder::getRoomId, item -> item));
            for (RoomData roomData : rooms) {
                if (map.get(roomData.getId()) == null) {
                    list.add(roomData);
                }
            }
        }
        return list;
    }

}
