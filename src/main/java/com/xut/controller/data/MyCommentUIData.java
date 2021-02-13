package com.xut.controller.data;

import com.xut.bean.Comment;
import com.xut.bean.RoomType;

public class MyCommentUIData {
    private Comment comment;
    private RoomTypeUIData roomType;

    public MyCommentUIData() {
    }

    public MyCommentUIData(Comment comment, RoomTypeUIData roomType) {
        this.comment = comment;
        this.roomType = roomType;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public RoomTypeUIData getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeUIData roomType) {
        this.roomType = roomType;
    }
}
