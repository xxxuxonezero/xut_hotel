package com.xut.controller.admin;

import com.xut.bean.Room;
import com.xut.bean.RoomData;
import com.xut.bean.RoomType;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.RoomService;
import com.xut.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class RoomController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping("/room")
    public ModelAndView roomView() {
        Map<String, Object> map = new HashMap<>();
        Result<List<RoomType>> listResult = roomTypeService.get(1, Integer.MAX_VALUE);
        if (listResult.isValid()) {
            map.put("types", listResult.getData());
        }
        return new ModelAndView("admin/roomMgr", map);
    }

    @GetMapping("/roomList")
    @ResponseBody
    public Result<List<RoomData>> getList(@RequestParam(value = "offset", required = false)Integer offset,
                                          @RequestParam(value = "pageSize", required = false)Integer pageSize,
                                          @RequestParam(value = "typeId", required = false) Integer typeId,
                                          @RequestParam(value = "status", required = false) Integer status) {
        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Result<List<RoomData>> result = new Result<>();
        Result<List<RoomData>> search = roomService.search(typeId == null ? null : Collections.singletonList(typeId),null, offset, pageSize);
        if (search.isNotValid()) {
            result.setCode(search.getCode());
            return result;
        }
        List<RoomData> roomDatas = search.getData();
        List<Integer> ids = roomDatas.stream().map(RoomData::getId).collect(Collectors.toList());
        //通过订单，计算客房的状态
        result.setData(roomDatas);
        return result;
    }

    @GetMapping("/editRoom")
    @ResponseBody
    public Result<RoomData> getRoomData(@RequestParam("id") Integer id) {
        return roomService.getById(id);
    }

    @ResponseBody
    @PostMapping("/addRoom")
    public NoneDataResult addRoom(@RequestBody Room room) {
        return roomService.create(room);
    }


    @PostMapping("/editRoom")
    @ResponseBody
    public NoneDataResult updateRoom(@RequestBody Room room) {
        NoneDataResult res = roomService.update(room);
        return res;
    }


    @ResponseBody
    @PostMapping("/deleteRoom")
    public NoneDataResult delete(@RequestParam("ids") List<Integer> ids) {
        return roomService.delete(ids);
    }
}
