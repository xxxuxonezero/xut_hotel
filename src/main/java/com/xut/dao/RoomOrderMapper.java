package com.xut.dao;

import com.xut.bean.RoomOrder;

import java.util.List;

public interface RoomOrderMapper {
    void create(RoomOrder roomOrder);

    void update(RoomOrder roomOrder);

    List<RoomOrder> search(List<Integer> orderIds);
}