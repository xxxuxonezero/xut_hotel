package com.xut.bean;

public class RoomData {
    private Integer id;
    private String roomNumber;
    private Integer floorNum;
    private Integer typeId;
    private String type;
    private String bed;
    private Integer status;
    private Integer maxPeople;


    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

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


    public Integer getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomData{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", floorNum=" + floorNum +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                ", bed='" + bed + '\'' +
                ", status=" + status +
                '}';
    }
}
