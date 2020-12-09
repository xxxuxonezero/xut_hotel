package com.xut.model;

public enum UserType {
    MANAGER(0, "管理员"), COMMON_USER(1, "用户");
    private Integer id;
    private String desc;

    UserType(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer id() {
        return id;
    }
    public String desc() {
        return desc;
    }
}
