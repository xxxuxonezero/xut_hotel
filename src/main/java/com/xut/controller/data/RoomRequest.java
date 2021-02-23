package com.xut.controller.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RoomRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutTime;
    private Integer roomTypeId;

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}
