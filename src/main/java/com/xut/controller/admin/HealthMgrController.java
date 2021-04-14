package com.xut.controller.admin;

import com.xut.bean.*;
import com.xut.controller.BaseController;
import com.xut.controller.data.HealthUIDate;
import com.xut.controller.data.OrderData;
import com.xut.model.NoneDataResult;
import com.xut.model.OrderStatus;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/health")
public class HealthMgrController extends BaseController {
    @Autowired
    OrderService orderService;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomOrderService roomOrderService;
    @Autowired
    HealthService healthService;


    private List<Integer> statuses = Arrays.asList(OrderStatus.IN_PROGRESS.id(), OrderStatus.HAS_CHECKED_IN.id());

    @GetMapping("")
    public ModelAndView health(HttpServletRequest request) {
        Map<String, Object> map = getUserModel(request);
        return new ModelAndView("/admin/healthMgr", map);
    }

    @GetMapping("/getList")
    public Result<Page<HealthUIDate>> getList(@RequestParam(value = "status", required = false) Integer status,
                                              @RequestParam(value = "offset", required = false, defaultValue = "1") Integer offset,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<HealthUIDate>> result = new Result<>();
        List<Order> orders = orderService.search(null, status == null ? statuses : Collections.singletonList(status));
        if (CollectionUtils.isEmpty(orders)) {
            return result;
        }

        Page<HealthUIDate> healthUIDatePage = new Page<>();
        healthUIDatePage.setTotalCount(orders.size());
        orders = orders.stream().skip((offset - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        List<Integer> ids = orders.stream().map(Order::getId).collect(Collectors.toList());

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

        Result<List<Health>> healthResult = healthService.search(ids);
        if (healthResult.isNotOK()) {
            result.setCode(healthResult.getCode());
            return result;
        }

        Map<Integer, Health> healthMap = healthResult.getData().stream().collect(Collectors.toMap(Health::getOrderId, Function.identity()));
        List<HealthUIDate> list = new ArrayList<>();
        for (Order order : orders) {
            if (roomDataMap.containsKey(order.getId())) {
                HealthUIDate health = new HealthUIDate();
                RoomData roomData = roomDataMap.get(order.getId());
                health.setOrderId(order.getId());
                health.setRoomId(roomData.getId());
                health.setRoomTypeDesc(roomData.getType());
                health.setCheckInPeople(roomData.getMaxPeople());
                health.setRoomNumber(roomData.getRoomNumber());
                health.setCheckIn(order.getStatus().equals(OrderStatus.HAS_CHECKED_IN.id()));
                health.setAllocatePeople(healthMap.containsKey(order.getId()));
                if (healthMap.containsKey(order.getId())) {
                    health.setClean(healthMap.get(order.getId()).getClean());
                }
                list.add(health);
            }
        }

        healthUIDatePage.setList(list);
        result.setData(healthUIDatePage);
        return result;
    }

    @PostMapping("/AllocateHealther")
    public NoneDataResult allocateHealther(@RequestParam("orderId") Integer orderId,
                                           @RequestParam("roomId") Integer roomId) {
        Health health
                = new Health();
        health.setOrderId(orderId);
        health.setRoomId(roomId);
        return healthService.batchCreate(Collections.singletonList(health));
    }

    @PostMapping("/AllocateAllRoom")
    public NoneDataResult allocateAll(@RequestParam(value = "status", required = false) Integer status) {
        NoneDataResult result = new NoneDataResult();
        List<Order> orders = orderService.search(null, status == null ? statuses : Collections.singletonList(status));
        if (CollectionUtils.isEmpty(orders)) {
            return result;
        }

        List<Integer> ids = orders.stream().map(Order::getId).collect(Collectors.toList());

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

        List<Health> list = new ArrayList<>();
        for (Order order : orders) {
            if (roomDataMap.containsKey(order.getId())) {
                Health health = new Health();
                RoomData roomData = roomDataMap.get(order.getId());
                health.setOrderId(order.getId());
                health.setRoomId(roomData.getId());
                list.add(health);
            }
        }

        return healthService.batchCreate(list);
    }

    @PostMapping("/Delete")
    public NoneDataResult delete(@RequestParam("orderId") Integer orderId) {
        return healthService.delete(orderId);
    }

    @PostMapping("/Update")
    public NoneDataResult update(@RequestBody Health health) {
        return healthService.update(health);
    }
}
