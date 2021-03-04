package com.xut.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xut.bean.Order;
import com.xut.bean.Room;
import com.xut.bean.RoomType;
import com.xut.controller.BaseController;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.OrderService;
import com.xut.service.RoomService;
import com.xut.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class RoomTypeController extends BaseController {
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    OrderService orderService;
    @Autowired
    RoomService roomService;

    private static final int USING = 3;

    @GetMapping("/roomType")
    public ModelAndView roomType(HttpServletRequest request) {
        return new ModelAndView("admin/roomTypeMgr", getUserModel(request));
    }

    @ResponseBody
    @GetMapping("/roomTypeList")
    public Result<Page<RoomType>> getRoomTypeList(@RequestParam(value = "offset", required = false, defaultValue = "1")Integer offset,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize) {
        Result<Page<RoomType>> result = roomTypeService.get(offset, pageSize);
        if (result.isNotValid()) {
            return result;
        }
        List<RoomType> roomTypes = result.getData().getList();
        List<Integer> typeIds = roomTypes.stream().map(RoomType::getId).collect(Collectors.toList());
        /**
         * 若是该房型目前有交易中的订单，则该房型不可删除
         */
        Result<Page<Order>> orderResult =
                orderService.search(null, typeIds, null, 2, 1, Integer.MAX_VALUE);
        if (orderResult.isValid()) {
            List<Order> orders = orderResult.getData().getList();
            Map<Integer, List<Order>> map = orders.stream().collect(Collectors.groupingBy(Order::getRoomTypeId));
            for (RoomType roomType : roomTypes) {
                if (map.get(roomType.getId()) != null) {
                    roomType.setStatus(USING);
                }
            }
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/addRoomType")
    public Result<Integer> addRoomType(@RequestBody RoomType roomType) {
        return roomTypeService.create(roomType);
    }

    @GetMapping("/editRoomType")
    @ResponseBody
    public Result<RoomType> editRoomType(@RequestParam("id") Integer id){
        Result<RoomType> roomTypeResult = roomTypeService.getById(id);
        return roomTypeResult;
    }

    @PostMapping("/editRoomType")
    @ResponseBody
    public NoneDataResult updateRoomType(@RequestBody RoomType roomType) {
        NoneDataResult res = roomTypeService.update(roomType);
        return res;
    }
    @GetMapping("/checkRoomType")
    @ResponseBody
    public boolean check(@RequestParam(value = "type") String type) {
        Result<RoomType> result = roomTypeService.getByName(type);
        return result.isEmpty();
    }


    @ResponseBody
    @PostMapping("/deleteRoomType")
    public NoneDataResult delete(@RequestParam("ids") List<Integer> ids) {
        NoneDataResult result = roomTypeService.delete(ids);
        if (result.isNotOK()) {
            return result;
        }
        result = roomService.deleteByTypeIds(ids);
        return result;
    }

}
