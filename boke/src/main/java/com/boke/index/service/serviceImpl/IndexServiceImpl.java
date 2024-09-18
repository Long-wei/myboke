package com.boke.index.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boke.index.dto.LoginIpDto;
import com.boke.index.dto.SignUpDto;
import com.boke.index.mapper.AccessRecordMapper;
import com.boke.index.mapper.LoginRecordMapper;
import com.boke.index.utils.MD5Utils;
import com.boke.index.vo.SignUpVo;
import com.boke.index.model.AccessRecord;
import com.boke.index.model.LoginRecord;
import com.boke.index.model.User;
import com.boke.index.dto.UserQueryDto;
import com.boke.index.vo.UserVo;
import com.boke.index.mapper.UserMapper;
import com.boke.index.service.IndexService;
import com.boke.index.utils.FormattedDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccessRecordMapper accessRecordMapper;
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Autowired
    private MD5Utils md5Utils;
    /**
     * 查询全部用户
     * @return
     */
    @Override
    public List<User> getAllUser() {
        List<User> allUser = userMapper.getAllUser();
        return allUser;
    }
    /**
     * 用户分页查询
     * @return
     */
    @Override
    public UserVo getAllUserQuery(UserQueryDto pageRequest) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(pageRequest.getUsername().equals(null),User::getUsername,pageRequest.getUsername());
        wrapper.eq(pageRequest.getSex().equals(null),User::getSex,pageRequest.getSex());
        if(pageRequest.getAge().equals(null)){
            wrapper.gt(pageRequest.getAge().substring(0,pageRequest.getAge().indexOf(",")) != null,User::getAge,Integer.parseInt(pageRequest.getAge().substring(0,pageRequest.getAge().indexOf(","))));
            wrapper.le(pageRequest.getAge().substring(pageRequest.getAge().indexOf(",")+1) != null,User::getAge,Integer.parseInt(pageRequest.getAge().substring(pageRequest.getAge().indexOf(",")+1)));
        }
        Page<User> page = new Page<>(pageRequest.getPageNum(),pageRequest.getPageSize());
        IPage<User> iPage = userMapper.selectPage(page,wrapper);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(pageRequest,userVo);
        userVo.setTotal((int)iPage.getTotal());
        userVo.setUsers(iPage.getRecords());
        return userVo;
    }

    /**
     * 用户注册
     * @param loginDto
     * @return
     */
    @Override
    public SignUpVo signup(SignUpDto signUpDto) {
        //查询要注册的账号是否已经存在
        User user = userMapper.selectUserByAccount(signUpDto.getAccount());
        SignUpVo signUpVo = new SignUpVo();
        if(user==null){
            user  = new User();
            user.setAccount(signUpDto.getAccount());
            String md5Password = md5Utils.encrypByMd5(signUpDto.getPassword());
            user.setPassword(md5Password);
            user.setAge(0);
            user.setSex("无");
            user.setUsername(signUpDto.getUsername());
            userMapper.createUser(user);
            signUpVo.setStatus(true);
            signUpVo.setIndex(true);
        }
        else {
            signUpVo.setStatus(false);
            signUpVo.setIndex(false);
        }
        return signUpVo;
    }

    /**
     * 保存访问用户ip
     * @param ip
     */
    @Override
    public void saveAccessUser(String ip) {
        if(ip==null){
            return;
        }
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setAccessTime(FormattedDateTime.getNowDateTime());
        accessRecord.setIp(ip);
        int record = accessRecordMapper.createRecord(accessRecord);
    }

    /**
     * 保存登录用户ip
     * @param ip
     */
    @Override
    public void saveLoginUser(LoginIpDto loginDto, String ip) {
        if(ip==null){
            return;
        }
        User user = userMapper.selectUserAllByAccount(loginDto.getAccount());
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setIp(ip);
        loginRecord.setUserId(user.getUserId());
        loginRecord.setLoginTime(FormattedDateTime.getNowDateTime());
        loginRecord.setUsername(user.getUsername());
        loginRecord.setUserPassword(user.getPassword());
        loginRecord.setLoginStatus(loginDto.isLoginStatus());
        loginRecordMapper.createLoginUserRecord(loginRecord);
    }

    /**
     * 查询用户信息通过用account
     * @param userAccount
     * @return
     */
    @Override
    public User selectUserByAccount(String userAccount) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",userAccount);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getOne(QueryWrapper<User> userQueryWrapper) {
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }

    @Override
    public boolean login(String username, String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", username);
        String md5Password = md5Utils.encrypByMd5(password);
        queryWrapper.eq("password", md5Password);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            return false;
        }
        return true;
    }

    @Override
    public User getUserById(String id) {
        if(id==null){
            return null;
        }
        if(id.equals("0")){
            User user = new User();
            user.setUserId("0");
            user.setUsername("人机");
            return user;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
