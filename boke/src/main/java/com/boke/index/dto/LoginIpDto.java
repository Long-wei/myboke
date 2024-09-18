package com.boke.index.dto;

public class LoginIpDto extends LoginDto{
    //登录是否成功
    private boolean loginStatus;

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
}
