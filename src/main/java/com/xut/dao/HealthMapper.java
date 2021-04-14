package com.xut.dao;

import com.xut.bean.Health;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthMapper {
    void batchCreate(@Param("healths") List<Health> health);

    void update(Health health);

    List<Health> search(@Param("orderIds") List<Integer> orderIds);

    void delete(Integer orderId);


}