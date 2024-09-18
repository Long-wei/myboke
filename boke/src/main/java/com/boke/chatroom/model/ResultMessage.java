package com.boke.chatroom.model;

/**
 * @version v1.0
 * @ClassName: ResultMessage
 * @Description: 服务器发送给浏览器的websocket数据
 * @Author: 二刺猿
 */
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private String toName;
    private Object message;//如果是系统消息是数组

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
}

