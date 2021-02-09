package com.xut.controller.user;

import com.xut.bean.RoomSetting;
import com.xut.bean.RoomType;
import com.xut.controller.BaseController;
import com.xut.controller.data.RoomTypeDetail;
import com.xut.controller.data.RoomTypeUIData;
import com.xut.model.Code;
import com.xut.model.Result;
import com.xut.service.RoomSettingService;
import com.xut.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserRoomTypeController  extends BaseController {
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    RoomSettingService roomSettingService;

    @GetMapping("/roomType")
    public ModelAndView roomType(HttpServletRequest request) {
        Map<String, Object> map =  getUserModel(request);
        return new ModelAndView("user/roomTypePreview", map);
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
    public ModelAndView detailPage(HttpServletRequest request) {
        return new ModelAndView("user/roomTypeDetail", getUserModel(request));
    }

    @ResponseBody
    @GetMapping("/roomType/detail")
    public Result<RoomTypeDetail> detail(@RequestParam("id") Integer id) {
        Result<RoomTypeDetail> result = new Result<>();
        if (id == null) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        Result<RoomType> roomTypeResult = roomTypeService.getById(id);
        if (roomTypeResult.isNotValid()) {
            result.setCode(roomTypeResult.getCode());
            return result;
        }
        RoomTypeDetail detail = new RoomTypeDetail();
        RoomTypeUIData room = new RoomTypeUIData(roomTypeResult.getData());
        detail.setRoomType(room);
        Result<RoomSetting> settingResult = roomSettingService.getByTypeId(id);
        if (settingResult.isNotOK()) {
            result.setCode(settingResult.getCode());
            return result;
        }
        detail.setSetting(settingResult.getData());
        result.setData(detail);
        return result;
    }
}
