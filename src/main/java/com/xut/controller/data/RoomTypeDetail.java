package com.xut.controller.data;

import com.xut.bean.RoomSetting;

import java.util.List;

public class RoomTypeDetail {
    RoomTypeUIData roomType;
    RoomSetting setting;

    public RoomTypeUIData getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeUIData roomType) {
        this.roomType = roomType;
    }

    public RoomSetting getSetting() {
        return setting;
    }

    public void setSetting(RoomSetting setting) {
        this.setting = setting;
    }
}
