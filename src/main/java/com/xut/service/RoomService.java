package com.xut.service;

import com.xut.bean.Room;
import com.xut.bean.RoomData;
import com.xut.dao.RoomMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public NoneDataResult deleteByTypeIds(List<Integer> typeIds) {
        NoneDataResult result = new NoneDataResult();
        try {
            roomMapper.deleteByTypeIds(typeIds);
        } catch (Exception e) {
            logger.error("RoomService delete error ", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }
    public Result<Page<RoomData>> search(List<Integer> typeIds, Integer id, int offset, int pageSize) {
        Result<Page<RoomData>> result = new Result<>();
        try {
            List<List<?>> data = roomMapper.search(typeIds, id, offset, pageSize);
            result.setData(new Page<RoomData>((Integer) data.get(1).get(0), (List<RoomData>) data.get(0)));
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
        Result<Page<RoomData>> search = search(null, id, 1, 1);
        if (search.isNotValid()) {
            result.setCode(search.getCode());
            return result;
        }
        result.setData(search.getData().getList().get(0));
        return result;
    }

    public Result<Page<RoomData>> getAll() {
        return search(null, null, 1, Integer.MAX_VALUE);
    }

    public Result<List<RoomData>> getByIds(List<Integer> ids) {
        Result<List<RoomData>> result = new Result<>();
        Result<Page<RoomData>> roomDataResult = getAll();
        List<RoomData> roomDatas = new ArrayList<>();
        if (roomDataResult.isNotValid()) {
            result.setCode(roomDataResult.getCode());
            return result;
        }

        List<RoomData> list = roomDataResult.getData().getList();
        for (RoomData roomData : list) {
            if (ids.contains(roomData.getId())) {
                roomDatas.add(roomData);
            }
        }
        result.setData(roomDatas);

        return result;
    }
}
