package com.boke.chatroom.vo;

import com.boke.index.model.User;

public class SearchUserVo extends User {
    private boolean OnlineStatus;

    public boolean getOnlineStatus() {
        return OnlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        OnlineStatus = onlineStatus;
    }
}
