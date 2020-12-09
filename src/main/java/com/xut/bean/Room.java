package com.xut.bean;

public class Room {
    private Integer id;
    private String roomNumber;
    private Integer typeId;
    private Integer floorNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    public Room(RoomData data) {
        if (data != null) {
            this.id = data.getId();
            this.typeId = data.getTypeId();
            this.floorNum = data.getFloorNum();
            this.roomNumber = data.getRoomNumber();
        }
    }

    public Room() {
    }
}
