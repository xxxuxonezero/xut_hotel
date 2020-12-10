package com.xut.dao;

import com.xut.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User getUserByIdAndPwd(@Param("identificationId") String identification, @Param("password") String password);

    void create(User user);

    void update(User user);

    List<User> getByIds(List<Integer> ids);

}
