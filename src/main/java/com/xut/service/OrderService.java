package com.xut.service;

import com.xut.bean.Order;
import com.xut.dao.OrderMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    OrderMapper orderMapper;

    public NoneDataResult create(Order order) {
        NoneDataResult result = new NoneDataResult();
        try {
            order.setUuid(UUID.randomUUID().toString());
            orderMapper.create(order);
        } catch (Exception e) {
            logger.error("OrderService create error ", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(Integer id) {
        NoneDataResult result = new NoneDataResult();
        try {
            orderMapper.delete(id);
        } catch (Exception e) {
            logger.error("OrderService delete error ", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }

    public Result<Page<Order>> search(List<Integer> userIds, List<Integer> roomTypeIds, Integer id, Integer status, int offset, int pageSize) {
        Result<Page<Order>> result = new Result<>();
        try {
            List<List<?>> orders = orderMapper.search(userIds, roomTypeIds, id, offset, status, pageSize);
            result.setData(new Page<Order>((Integer) orders.get(1).get(0), (List<Order>) orders.get(0)));
        } catch (Exception e) {
            logger.error("OrderService search error");
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(Order order) {
        NoneDataResult result = new NoneDataResult();
        if (order == null) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        try {
            orderMapper.update(order);
        } catch (Exception e) {
            logger.error("OrderService update error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

}
