package com.xut.service;

import com.xut.bean.Client;
import com.xut.dao.ClientMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientService {
    Logger logger = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    ClientMapper clientMapper;

    public NoneDataResult create(List<Client> clients) {
        NoneDataResult result = new NoneDataResult();
        try {
            clientMapper.create(clients);
        } catch (Exception e) {
            logger.error("ClientService create error", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public Result<List<Client>> search(List<Integer> orderIds) {
        Result<List<Client>> result = new Result<>();
        try {
            List<Client> clients = clientMapper.search(orderIds);
            result.setData(clients);
        } catch (Exception e) {
            logger.error("ClientService search error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<Map<Integer, List<Client>>> getClientMap(List<Integer> orderIds) {
        Result<Map<Integer, List<Client>>> result = new Result<>();
        Result<List<Client>> clientsResult = search(orderIds);
        if (clientsResult.isNotValid()) {
            result.setCode(clientsResult.getCode());
            return result;
        }
        List<Client> data = clientsResult.getData();
        Map<Integer, List<Client>> map = data.stream().collect(Collectors.groupingBy(Client::getOrderId));
        result.setData(map);
        return result;
    }
}
