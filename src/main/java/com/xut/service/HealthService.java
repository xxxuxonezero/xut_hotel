package com.xut.service;

import com.xut.bean.Client;
import com.xut.bean.Health;
import com.xut.dao.ClientMapper;
import com.xut.dao.HealthMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HealthService {
    Logger logger = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    HealthMapper healthMapper;

    public NoneDataResult batchCreate(List<Health> healths) {
        NoneDataResult result = new NoneDataResult();
        try {
            healthMapper.batchCreate(healths);
        } catch (Exception e) {
            logger.error("error", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(Health health) {
        NoneDataResult result = new NoneDataResult();
        try {
            healthMapper.update(health);
        } catch (Exception e) {
            logger.error("error", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public Result<List<Health>> search(List<Integer> orderIds) {
        Result<List<Health>> result = new Result<>();
        try {
            List<Health> healths = healthMapper.search(orderIds);
            result.setData(healths);
        } catch (Exception e) {
            logger.error("error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(Integer orderId) {
        NoneDataResult result = new NoneDataResult();
        if (orderId == null) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        try {
            healthMapper.delete(orderId);
        } catch (Exception e) {
            logger.error("delete error", e);
        }
        return result;
    }
}
