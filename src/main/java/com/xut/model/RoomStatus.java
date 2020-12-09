package com.xut.model;

public enum RoomStatus {
    EMPTY(0, "空闲"),HAVE_LIVED(1, "已入住"),HAVE_ORDERED(2, "已预定");

    private Integer id;
    private String desc;

    RoomStatus(Integer id, String desc) {
    }

    public Integer id() {
        return id;
    }
    public String desc() {
        return desc;
    }
}
