package com.xut.dao;

import com.xut.bean.Client;

import java.util.List;

public interface ClientMapper {
    List<Client> search(List<Integer> orderId);

    void create(List<Client> clients);
}
