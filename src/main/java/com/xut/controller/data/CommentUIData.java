package com.xut.controller.data;

import com.xut.bean.Comment;
import com.xut.bean.Reply;
import com.xut.bean.User;

import java.util.List;

public class CommentUIData {
    private Comment comment;
    private User author;
    private int replyNum;
    private List<ReplyUIData> replys;

    public CommentUIData(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public List<ReplyUIData> getReplys() {
        return replys;
    }

    public void setReplys(List<ReplyUIData> replys) {
        this.replys = replys;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
