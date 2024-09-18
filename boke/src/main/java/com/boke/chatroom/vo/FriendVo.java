package com.boke.chatroom.vo;

import java.util.List;

public class FriendVo {
    private String id;
    private String userId;
    private List<String> friendId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFriendId() {
        return friendId;
    }

    public void setFriendId(List<String> friendId) {
        this.friendId = friendId;
    }
}
