package com.app.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public class Dialog {
    private String dialogName;
    private List<MessageItem> messageArray = new ArrayList<MessageItem>();

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }
    public String getDialgName() {
        return dialogName;
    }

    public void addMessage(MessageItem message) {
        messageArray.add(message);
    }

    public MessageItem getLastMessage() {
        if(messageArray.size() > 0) {
            return messageArray.get(messageArray.size() - 1);
        } else {
            return null;
        }
    }
}
