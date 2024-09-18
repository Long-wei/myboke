package com.boke.chatroom.model;

/**
 * @version v1.0
 * @ClassName: Message
 * @Description: 浏览器发送给服务器的websocket数据
 * @Author: 二刺猿
 */
public class Message {
    private String toName;
    private String message;
    private String token;
    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

