package com.xut.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class RoomType {
    private Integer id;
    private String type;
    private String imgs;
    private Double price;
    private Integer maxPeople;
    private Integer size;
    private Integer hasFood;
    private String bed;
    private String floor;
    private Integer hasWindow;
    private Integer smoke;
    private List<String> imgList;

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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
        if (imgs != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.imgList = mapper.readValue(imgs, List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
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

    public Integer setHasFood() {
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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) throws JsonProcessingException {
        if (CollectionUtils.isNotEmpty(imgList)) {
            ObjectMapper om = new ObjectMapper();
            String s = om.writeValueAsString(imgList);
            this.imgs = s;
        }
        this.imgList = imgList;
    }

    public Integer getHasFood() {
        return hasFood;
    }
}
