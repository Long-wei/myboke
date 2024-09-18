package com.boke.index.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("access_record")
public class AccessRecord {
    private String id;
    private String ip;
    private String accessTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String time) {
        this.accessTime = time;
    }
}