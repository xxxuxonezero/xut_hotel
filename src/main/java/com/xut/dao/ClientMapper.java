package com.xut.dao;

import com.xut.bean.Client;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClientMapper {
    List<Client> search(List<Integer> orderId);

    void create(List<Client> clients);

    void delete(@Param("orderId") Integer orderId);
}
