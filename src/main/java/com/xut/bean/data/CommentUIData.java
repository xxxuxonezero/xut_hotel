package com.xut.bean.data;

import com.xut.bean.User;

import java.util.Date;
import java.util.List;

public class CommentUIData {
    private Integer id;
    private Integer typeId;
    private String description;
    private Integer userId;
    private Date createdTime;
    private Date updatedTime;
    private User author;
    private List<ReplyUIData> replys;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<ReplyUIData> getReplys() {
        return replys;
    }

    public void setReplys(List<ReplyUIData> replys) {
        this.replys = replys;
    }
}
