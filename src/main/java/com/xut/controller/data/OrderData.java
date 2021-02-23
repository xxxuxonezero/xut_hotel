package com.xut.controller.data;

import com.xut.bean.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class OrderData {
    private RoomType roomType;
    private Order order;
    private List<Client> clients;
    private RoomData roomData;

    public OrderData() {
    }

    public OrderData(RoomType roomType, Order order, List<Client> clients) {
        this.roomType = roomType;
        this.order = order;
        this.clients = clients;
    }

    public OrderData(RoomType roomType, Order order, List<Client> clients, RoomData roomData) {
        this.roomType = roomType;
        this.order = order;
        this.clients = clients;
        this.roomData = roomData;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
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
