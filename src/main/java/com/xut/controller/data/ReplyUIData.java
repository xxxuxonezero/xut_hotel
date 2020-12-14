package com.xut.controller.data;

import com.xut.bean.Reply;
import com.xut.bean.User;

public class ReplyUIData {
    private Reply reply;
    private User author;

    public ReplyUIData(Reply reply, User author) {
        this.reply = reply;
        this.author = author;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
