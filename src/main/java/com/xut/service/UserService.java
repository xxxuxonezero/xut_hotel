package com.xut.service;

import com.xut.bean.User;
import com.xut.dao.UserMapper;
import com.xut.model.Code;
import com.xut.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserMapper userMapper;

    public Result<List<User>> getByIds(List<Integer> ids) {
        Result<List<User>> result = new Result<>();
        try {
            List<User> users = userMapper.getByIds(ids);
            result.setData(users);
        } catch (Exception e) {
            logger.error("UserService getByIds error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }
}
