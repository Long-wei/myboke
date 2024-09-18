package com.boke.index.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boke.index.dto.LoginIpDto;
import com.boke.index.dto.SignUpDto;
import com.boke.index.vo.SignUpVo;
import com.boke.index.model.User;
import com.boke.index.dto.UserQueryDto;
import com.boke.index.vo.UserVo;

import java.util.List;

public interface IndexService {
    /**
     * 查询全部用户
     * @return
     */
    public List<User> getAllUser();
    /**
     * 用户分页查询
     * @return
     */
    public UserVo getAllUserQuery(UserQueryDto pageRequest);
    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    /**
     * 用户注册
     * @param signUpDto
     * @return
     */
    public SignUpVo signup(SignUpDto signUpDto);
    /**
     * 保存访问用户ip
     * @param ip
     */
    public void saveAccessUser(String ip);

    /**
     * 保存登录用户ip
     * @param ip
     */
    public void saveLoginUser(LoginIpDto loginDto, String ip);

    /**
     * 查询用户信息通过用account
     * @param userAccount
     * @return
     */
    public User selectUserByAccount(String userAccount);

    User getOne(QueryWrapper<User> userQueryWrapper);

    public boolean login(String username, String password);

    public User getUserById(String id);
}
