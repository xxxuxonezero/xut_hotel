package com.xut.bean;

public class RoomSetting {
    private Integer id;
    private Integer typeId;
    private String wifi;
    private String food;
    private String spot;
    private String facilities;
    private String airConditioner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(String airConditioner) {
        this.airConditioner = airConditioner;
    }
}
