package com.xut.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xut.bean.RoomType;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class RoomTypeController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping("/roomType")
    public String roomType() {
        return "admin/roomTypeMgr";
    }

    @ResponseBody
    @GetMapping("/roomTypeList")
    public Result<List<RoomType>> getRoomTypeList(@RequestParam(value = "offset", required = false)Integer offset,
                                          @RequestParam(value = "pageSize", required = false)Integer pageSize) {

        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        return roomTypeService.get(offset, pageSize);
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
        return roomTypeService.delete(ids);
    }

}
