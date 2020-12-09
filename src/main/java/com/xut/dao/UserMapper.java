package com.xut.dao;

import com.xut.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserByIdAndPwd(@Param("identificationId") String identification, @Param("password") String password);

    void create(User user);

    void update(User user);

    void getById(Integer id);

}
