package com.boke.index.service;

import com.boke.index.model.User;

public interface PersonalInformationService {
    /**
     * 根据账号获得用户信息
     * @return
     */
//    public User getUserByAccount();

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public int updateUser(User user);
}
