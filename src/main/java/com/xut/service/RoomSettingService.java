package com.xut.service;

import com.xut.bean.RoomSetting;
import com.xut.dao.RoomSettingMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomSettingService {
    Logger logger = LoggerFactory.getLogger(RoomSettingService.class);
    @Autowired
    RoomSettingMapper roomSettingMapper;

    public Result<RoomSetting> getByTypeId(Integer typeId) {
        Result<RoomSetting> result = new Result<>();
        if (typeId == null) {
            return result;
        }
        try {
            RoomSetting roomSetting = roomSettingMapper.getByTypeId(typeId);
            result.setData(roomSetting);
        } catch (Exception e) {
            logger.error("RoomSettingService getByTypeId error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(List<Integer> typeIds) {
        NoneDataResult result = new NoneDataResult();
        if (CollectionUtils.isEmpty(typeIds)) {
            return result;
        }
        try {
            roomSettingMapper.delete(typeIds);
        } catch (Exception e) {
            logger.error("RoomSettingService delete error ", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }
}
