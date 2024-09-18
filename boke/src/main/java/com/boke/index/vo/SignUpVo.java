package com.boke.index.vo;

public class SignUpVo {
    private boolean status;
    //index表示查询账号在数据库是否存在
    //
    private boolean index;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }
}
