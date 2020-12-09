package com.xut.controller;

import com.xut.bean.RoomType;
import com.xut.controller.data.RoomTypeUIData;
import com.xut.model.Code;
import com.xut.model.Result;
import com.xut.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RoomTypeController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping("/roomType")
    public String roomType() {
        return "user/roomTypePreivew";
    }

    @GetMapping("/roomTypeList")
    @ResponseBody
    public Result<List<RoomTypeUIData>> getRoomTypeList(@RequestParam(value = "offset", required = false)Integer offset,
                                                        @RequestParam(value = "pageSize", required = false)Integer pageSize) {

        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Result<List<RoomType>> roomTypesResult = roomTypeService.get(offset, pageSize);
        Result<List<RoomTypeUIData>> result = new Result<>();
        if (roomTypesResult.isNotValid()) {
            result.setCode(roomTypesResult.getCode());
            return result;
        }
        List<RoomTypeUIData> list = roomTypesResult.getData().stream().map(RoomTypeUIData::new).collect(Collectors.toList());
        result.setData(list);
        return result;
    }

    @GetMapping("/roomTypeDetail")
    public String detailPage() {
        return "user/roomTypeDetail";
    }

    @GetMapping("/roomType/detail")
    public Result<RoomTypeUIData> detail(@RequestParam("id") Integer id) {
        Result<RoomTypeUIData> result = new Result<>();
        if (id == null) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        Result<RoomType> roomTypeResult = roomTypeService.getById(id);
        if (roomTypeResult.isNotValid()) {
            result.setCode(roomTypeResult.getCode());
            return result;
        }
        RoomTypeUIData room = new RoomTypeUIData(roomTypeResult.getData());
        result.setData(room);
        return result;
    }
}
