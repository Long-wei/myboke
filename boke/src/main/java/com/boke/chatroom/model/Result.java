package com.boke.chatroom.model;

/**
 * @version v1.0
 * @ClassName: Result
 * @Description: 用于登陆响应回给浏览器的数据
 * @Author: 二刺猿
 */
public class Result {
    private boolean flag;
    private String message;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

