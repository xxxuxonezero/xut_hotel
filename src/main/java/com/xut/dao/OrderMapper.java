package com.xut.dao;

import com.xut.bean.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    void create(Order order);

    void update(Order order);

    void delete(Integer id);

    List<Order> search(@Param("userId") Integer userId, @Param("roomTypeIds") List<Integer> typeIds,
                       @Param("id") Integer id, @Param("offset") int offset,
                       @Param("status") Integer status,
                       @Param("pageSize") int pageSize);

    Integer searchCount(@Param("userId") Integer userId, @Param("roomTypeIds") List<Integer> typeIds,
                        @Param("id") Integer id, @Param("offset") int offset,
                        @Param("status") Integer status,
                        @Param("pageSize") int pageSize);
}
