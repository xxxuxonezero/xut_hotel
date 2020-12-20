package com.xut.controller.data;

import com.xut.bean.Client;
import com.xut.bean.Order;
import com.xut.bean.RoomType;

import java.util.Date;
import java.util.List;

public class OrderData {
    private RoomType roomType;
    private Order order;
    private List<Client> clients;

    public OrderData() {
    }

    public OrderData(RoomType roomType, Order order, List<Client> clients) {
        this.roomType = roomType;
        this.order = order;
        this.clients = clients;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
