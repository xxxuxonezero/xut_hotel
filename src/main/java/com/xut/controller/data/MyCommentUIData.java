package com.xut.controller.data;

import com.xut.bean.Comment;
import com.xut.bean.RoomType;

public class MyCommentUIData {
    private Comment comment;
    private RoomType roomType;

    public MyCommentUIData() {
    }

    public MyCommentUIData(Comment comment, RoomType roomType) {
        this.comment = comment;
        this.roomType = roomType;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
