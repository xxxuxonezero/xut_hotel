package com.xut.service;

import com.xut.bean.RoomType;
import com.xut.dao.RoomTypeMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    public Result<Page<RoomType>> get(int offset, int pageSize) {
        Result<Page<RoomType>> result = new Result<>();
        try {
            List<List<?>> roomTypes = roomTypeMapper.get(null, offset, pageSize);
            result.setData(new Page<RoomType>((Integer) roomTypes.get(1).get(0), (List<RoomType>)roomTypes.get(0)));
        } catch (Exception e) {
            logger.error("RoomTypeService get error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<List<RoomType>> getList(int offset, int pageSize) {
        Result<List<RoomType>> result = new Result<>();
        Result<Page<RoomType>> pageResult = get(offset, pageSize);
        if (pageResult.isNotValid()) {
            result.setCode(pageResult.getCode());
            return result;
        }
        result.setData(pageResult.getData().getList());
        return result;
    }

    public Result<RoomType> getByName(String type) {
        Result<RoomType> result = new Result<>();
        if (type == null) {
            return result;
        }
        try {
            List<List<?>> list = roomTypeMapper.get(type, 1, 1);
            List<RoomType> roomType = (List<RoomType>) list.get(0);
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

    public Map<Integer, RoomType> getMapByTypeId() {
        Map<Integer, RoomType> map = new HashMap<>();
        Result<Page<RoomType>> roomTypeResult = get(1, Integer.MAX_VALUE);
        if (roomTypeResult.isNotValid()) {
            return map;
        }
        List<RoomType> roomTypes = roomTypeResult.getData().getList();
        if (CollectionUtils.isEmpty(roomTypes)) {
            return map;
        }
        map = roomTypes.stream().collect(Collectors.toMap(RoomType::getId, item -> item));
        return map;
    }

}
