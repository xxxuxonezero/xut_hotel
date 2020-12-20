package com.xut.controller.admin;

import com.xut.bean.*;
import com.xut.controller.data.OrderData;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.ClientService;
import com.xut.service.OrderService;
import com.xut.service.RoomTypeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class OrderController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;
    @Autowired
    RoomTypeService roomTypeService;


    @GetMapping("/order")
    public ModelAndView orderPage() {
        Map<String, Object> map = new HashMap<>();
        Result<List<RoomType>> roomTypesResult = roomTypeService.get(1, Integer.MAX_VALUE);
        if (roomTypesResult.isValid()) {
            map.put("types", roomTypesResult.getData());
        }
        return new ModelAndView("/admin/orderMgr", map);
    }

    @GetMapping("/orderList")
    @ResponseBody
    public Result<Page<OrderData>> getOrderList(@RequestParam(value = "typeId", required = false) Integer roomTypeId,
                                                @RequestParam(value = "status", required = false) Integer status,
                                                @RequestParam(value = "offset", required = false) Integer offset,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Result<Page<OrderData>> result = new Result<>();
        Result<Page<Order>> ordersResult = orderService.search(null, roomTypeId, null, status, offset, pageSize);
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
        Page<OrderData> orderDataPage = new Page<>();
        orderDataPage.setTotalCount(page.getTotalCount());
        orderDataPage.setList(list);
        result.setData(orderDataPage);

        return result;
    }

}
