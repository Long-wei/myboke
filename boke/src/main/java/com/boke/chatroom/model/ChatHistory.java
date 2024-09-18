package com.boke.chatroom.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("chat_history")
public class ChatHistory {
    private String id;
    private String fromName;
    private String toName;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String historyMessage) {
        this.message = historyMessage;
    }
}
