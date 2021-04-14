package com.xut.controller.data;

public class HealthUIDate {
    private Integer orderId;
    private Integer roomId;
    private String roomNumber;
    private String roomTypeDesc;
    private Boolean checkIn;
    private Integer checkInPeople;
    private Boolean clean;
    private Boolean allocatePeople;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeDesc() {
        return roomTypeDesc;
    }

    public void setRoomTypeDesc(String roomTypeDesc) {
        this.roomTypeDesc = roomTypeDesc;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getCheckInPeople() {
        return checkInPeople;
    }

    public void setCheckInPeople(Integer checkInPeople) {
        this.checkInPeople = checkInPeople;
    }

    public Boolean getClean() {
        return clean;
    }

    public void setClean(Boolean clean) {
        this.clean = clean;
    }

    public Boolean getAllocatePeople() {
        return allocatePeople;
    }

    public void setAllocatePeople(Boolean allocatePeople) {
        this.allocatePeople = allocatePeople;
    }
}
