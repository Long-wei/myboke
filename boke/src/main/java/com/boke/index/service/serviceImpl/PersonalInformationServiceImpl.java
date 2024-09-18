package com.boke.index.service.serviceImpl;

import com.boke.index.mapper.UserMapper;
import com.boke.index.service.PersonalInformationService;
import com.boke.index.model.User;
import com.boke.index.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MD5Utils md5Utils;
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        if(user==null){
            return 0;
        }
        if(user.getPassword() != null){
            user.setPassword(md5Utils.encrypByMd5(user.getPassword()));
        }
        int i = userMapper.updateUser(user);
        return i;
    }
}
