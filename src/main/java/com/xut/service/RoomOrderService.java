package com.xut.service;
import com.xut.bean.RoomOrder;
import com.xut.dao.RoomOrderMapper;
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
public class RoomOrderService {
    private static final Logger logger = LoggerFactory.getLogger(RoomOrder.class);
    @Autowired
    private RoomOrderMapper roomOrderMapper;

    public NoneDataResult create(RoomOrder roomOrder) {
        NoneDataResult result = new NoneDataResult();
        try {
            if (roomOrder != null) {
                roomOrderMapper.create(roomOrder);
            }
        } catch (Exception e) {
            logger.error("error", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(RoomOrder roomOrder) {
        NoneDataResult result = new NoneDataResult();
        try {
            if (roomOrder != null) {
                roomOrderMapper.update(roomOrder);
            }
        } catch (Exception e) {
            logger.error("error", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public Result<List<RoomOrder>> search(List<Integer> orderIds) {
        Result<List<RoomOrder>> result = new Result<>();
        if (CollectionUtils.isEmpty(orderIds)) {
            return result;
        }
        try {
            List<RoomOrder> list = roomOrderMapper.search(orderIds);
            result.setData(list);
        } catch (Exception e) {
            logger.error("error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

}
