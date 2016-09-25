package com.app.hrms.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/6.
 */
public class MessageItem {
    public String text = "";
    public Date dateSent = new Date();

    public MessageItem(String text) {
        this.text = text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
    public Date getDateSent() {
        return dateSent;
    }
}
