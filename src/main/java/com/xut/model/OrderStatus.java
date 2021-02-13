package com.xut.model;

public enum OrderStatus {
    CANCELED(0, "已取消"),FINISHED(1, "已完成"),IN_PROGRESS(2, "交易中"), HAS_CHECKED_IN(3, "已锁定");

    private Integer id;
    private String desc;

    OrderStatus(Integer id, String desc) {
    }

    public Integer id() {
        return id;
    }
    public String desc() {
        return desc;
    }
}
