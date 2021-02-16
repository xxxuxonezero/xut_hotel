package com.xut.service;

import com.xut.bean.User;
import com.xut.dao.UserMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
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

    public Result<List<User>> getAll() {
        Result<List<User>> result = new Result<>();
        try {
            List<User> users = userMapper.getAll();
            result.setData(users);
        } catch (Exception e) {
            logger.error("UserService getAll error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<Page<User>> search(Integer type, Integer old,int offset, int pageSize) {
        Result<Page<User>> result = new Result<>();
        try {
            List<List<?>> search = userMapper.search(type, old, offset, pageSize);
            result.setData(new Page<User>((Integer) search.get(1).get(0), (List<User>)search.get(0)));
        }catch (Exception e) {
            logger.error("UserService search error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<Page<User>> search(Integer type, int offset, int pageSize) {
        return search(type, offset, pageSize);
    }

    public Result<User> getByIdentificationIdAndPwd(String identificationId, String pwd) {
        Result<User> result = new Result<>();
        try {
            User user = userMapper.getUserByIdAndPwd(identificationId, pwd);
            result.setData(user);
        } catch (Exception e) {
            logger.error("UserService getByIdentificationIdAndPwd error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult create(User user) {
        NoneDataResult result = new NoneDataResult();
        try {
            userMapper.create(user);
        } catch (Exception e) {
            logger.error("UserService create error ", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult update(User user) {
        NoneDataResult result = new NoneDataResult();
        try {
            userMapper.update(user);
        } catch (Exception e) {
            logger.error("UserService update error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public NoneDataResult resetPassword(String password, Integer id) {
        NoneDataResult result = new NoneDataResult();
        if (password == null || id == null) {
            result.setCode(Code.INVALID_PARAM);
            return result;
        }
        try {
            userMapper.resetPassword(password, id);
        } catch (Exception e) {
            logger.error("UserService resetPassword error ", e);
            result.setCode(Code.DATABASE_UPDATE_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(List<Integer> ids) {
        NoneDataResult result = new NoneDataResult();
        try {
            userMapper.delete(ids);
        } catch (Exception e) {
            logger.error("UserService delete error ", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }
}
