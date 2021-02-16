package com.xut.controller.user;

import com.xut.bean.Client;
import com.xut.bean.Order;
import com.xut.bean.RoomType;
import com.xut.controller.BaseController;
import com.xut.controller.auth.AuthUtil;
import com.xut.controller.data.OrderData;
import com.xut.filter.Identity;
import com.xut.model.*;
import com.xut.service.ClientService;
import com.xut.service.OrderService;
import com.xut.service.RoomTypeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/MyOrder")
    public ModelAndView myOrder(HttpServletRequest request) {
        return new ModelAndView("user/myOrder", getUserModel(request));
    }

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody OrderData orderData, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        Order order = orderData.getOrder();
        order.setUserId(identity.getUserId());
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

}
