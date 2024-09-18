package com.boke.admin.service;

import com.boke.index.model.AccessRecord;
import com.boke.index.model.LoginRecord;
import com.boke.index.model.User;
import com.boke.index.utils.Result;

import java.util.List;

public interface AdminService {
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    public Result deleteUser(String id);
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public Result updateUser(User user);

    /**
     * 获得登录用户数据
     * @return
     */
    public List<LoginRecord> getLoginRecord();
    public List<AccessRecord> getAccessRecord();
}
