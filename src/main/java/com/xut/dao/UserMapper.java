package com.xut.dao;

import com.xut.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User getUserByIdAndPwd(@Param("identificationId") String identification, @Param("password") String password);

    void create(User user);

    void update(User user);

    List<User> getByIds(List<Integer> ids);

    void resetPassword(@Param("password") String password, @Param("id") Integer id);

    List<User> getAll();

    List<List<?>> search(@Param("type") Integer type,
                         @Param("old") Integer old,
                         @Param("offset") int offset,
                         @Param("pageSize") int pageSize);

    void delete(List<Integer> ids);

}
