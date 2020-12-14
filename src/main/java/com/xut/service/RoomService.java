package com.xut.service;

import com.xut.bean.Room;
import com.xut.bean.RoomData;
import com.xut.dao.RoomMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    Logger logger = LoggerFactory.getLogger(RoomService.class);
    @Autowired
    RoomMapper roomMapper;

    public NoneDataResult create(Room room) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomMapper.create(room);
        } catch (Exception e) {
            logger.error("RoomService create error ", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(List<Integer> ids) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomMapper.delete(ids);
        } catch (Exception e) {
            logger.error("RoomService delete error ", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }

    public Result<List<RoomData>> search(List<Integer> typeIds, Integer id, int offset, int pageSize) {
        Result<List<RoomData>> result = new Result<>();
        try {
            List<RoomData> roomData = roomMapper.search(typeIds, id, offset, pageSize);
            result.setData(roomData);
        } catch (Exception e) {
            logger.error("RoomService search error");
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(Room room) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomMapper.update(room);
        } catch (Exception e) {
            logger.error("RoomService update error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public Result<RoomData> getById(Integer id) {
        Result<RoomData> result = new Result<>();
        Result<List<RoomData>> search = search(null, id, 1, 1);
        if (search.isNotValid()) {
            result.setCode(search.getCode());
            return result;
        }
        result.setData(search.getData().get(0));
        return result;
    }
}
