package com.xut.controller.data;

import com.xut.bean.RoomType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class RoomTypeUIData {
    private Integer id;
    private String type;
    private Double price;
    private Integer maxPeople;
    private Integer size;
    private Integer hasFood;
    private String bed;
    private String floor;
    private Integer hasWindow;
    private Integer smoke;
    private String cover;
    private List<String> imgList;

    public RoomTypeUIData() {

    }

    public RoomTypeUIData(RoomType roomType) {
        if (roomType != null) {
            this.id = roomType.getId();
            this.type = roomType.getType();
            this.bed = roomType.getBed();
            this.floor = roomType.getFloor();
            this.hasFood = roomType.getHasFood();
            this.hasWindow = roomType.getHasWindow();
            this.smoke = roomType.getSmoke();
            this.maxPeople = roomType.getMaxPeople();
            this.imgList = roomType.getImgList();
            if (CollectionUtils.isNotEmpty(roomType.getImgList())) {
                this.cover = roomType.getImgList().get(0);
            }
        }
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getHasFood() {
        return hasFood;
    }

    public void setHasFood(Integer hasFood) {
        this.hasFood = hasFood;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Integer getHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(Integer hasWindow) {
        this.hasWindow = hasWindow;
    }

    public Integer getSmoke() {
        return smoke;
    }

    public void setSmoke(Integer smoke) {
        this.smoke = smoke;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
