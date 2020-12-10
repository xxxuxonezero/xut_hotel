package com.xut.service;

import com.xut.bean.RoomType;
import com.xut.dao.RoomTypeMapper;
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
public class RoomTypeService {
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
    @Autowired
    RoomTypeMapper roomTypeMapper;
    @Autowired
    RoomSettingService roomSettingService;

    public Result<Integer> create(RoomType roomType) {
        Result<Integer> result = new Result<>();
        try {
            roomTypeMapper.create(roomType);
        } catch (Exception e) {
            logger.error("RoomTypeService create error ", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        result.setData(roomType.getId());

        return result;
    }

    public Result<RoomType> getById(Integer id) {
        Result<RoomType> result = new Result<>();
        if (id == null) {
            return result;
        }
        try {
            RoomType roomType = roomTypeMapper.getById(id);
            result.setData(roomType);
        } catch (Exception e) {
            logger.error("RoomTypeService getById error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<List<RoomType>> get(int offset, int pageSize) {
        Result<List<RoomType>> result = new Result<>();
        try {
            List<RoomType> roomTypes = roomTypeMapper.get(null, offset, pageSize);
            result.setData(roomTypes);
        } catch (Exception e) {
            logger.error("RoomTypeService get error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<RoomType> getByName(String type) {
        Result<RoomType> result = new Result<>();
        if (type == null) {
            return result;
        }
        try {
            List<RoomType> roomType = roomTypeMapper.get(type, 1, 1);
            if (CollectionUtils.isNotEmpty(roomType)) {
                result.setData(roomType.get(0));
            }
        } catch (Exception e) {
            logger.error("RoomTypeService getByName error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(RoomType roomType) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomTypeMapper.update(roomType);
        } catch (Exception e) {
            logger.error("RoomTypeService update error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(List<Integer> ids) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomTypeMapper.delete(ids);
            roomSettingService.delete(ids);
        } catch (Exception e) {
            logger.error("RoomTypeService delete error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

}
