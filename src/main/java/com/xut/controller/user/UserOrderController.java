package com.xut.controller.user;

import com.xut.bean.Client;
import com.xut.bean.Order;
import com.xut.bean.RoomType;
import com.xut.controller.data.OrderData;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.ClientService;
import com.xut.service.OrderService;
import com.xut.service.RoomTypeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/order")
public class UserOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;
    @Autowired
    RoomTypeService roomTypeService;

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody OrderData orderData) {
        NoneDataResult result = new NoneDataResult();
        Order order = orderData.getOrder();
        List<Client> clients = orderData.getClients();
        result = orderService.create(order);
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

    @GetMapping("/list")
    public Result<List<OrderData>> getList(@RequestParam("userId") Integer userId) {
        Result<List<OrderData>> result = new Result<>();
        Result<Page<Order>> ordersResult = orderService.search(userId, null, null, null, 1, Integer.MAX_VALUE);
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
        Result<List<RoomType>> roomTypeResult = roomTypeService.get(1, Integer.MAX_VALUE);
        if (roomTypeResult.isNotValid()) {
            result.setCode(roomTypeResult.getCode());
            return result;
        }
        Map<Integer, RoomType> roomTypeMap =
                roomTypeResult.getData().stream().collect(Collectors.toMap(RoomType::getId, item -> item));
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
        NoneDataResult result = orderService.delete(id);
        return result;
    }

}
