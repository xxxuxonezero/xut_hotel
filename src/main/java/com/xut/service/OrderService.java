package com.xut.service;

import com.xut.bean.Order;
import com.xut.dao.OrderMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Result<Page<Order>> search(Integer userId, Integer roomTypeId, Integer id, Integer status, int offset, int pageSize) {
        Result<Page<Order>> result = new Result<>();
        try {
            List<Order> orders = orderMapper.search(userId, roomTypeId, id, offset, status, pageSize);
            int count = orderMapper.searchCount(userId, roomTypeId, id, offset, status, pageSize);
            Page<Order> page = new Page<>();
            page.setList(orders);
            page.setTotalCount(count);
            result.setData(page);
        } catch (Exception e) {
            logger.error("OrderService search error");
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(Order order) {
        NoneDataResult result = new NoneDataResult();
        try {
            orderMapper.update(order);
        } catch (Exception e) {
            logger.error("OrderService update error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

}
