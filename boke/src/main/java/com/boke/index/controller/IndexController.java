package com.boke.index.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boke.index.dto.LoginIpDto;
import com.boke.index.dto.SignUpDto;
import com.boke.index.utils.MD5Utils;
import com.boke.index.utils.TokenUtil;
import com.boke.index.vo.SignUpVo;
import com.boke.index.model.User;
import com.boke.index.dto.UserQueryDto;
import com.boke.index.vo.UserVo;
import com.boke.index.service.IndexService;
import com.boke.index.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//@CrossOrigin("${cros.url}")
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private MD5Utils md5Utils;
    /**
     * 查询全部用户
     * @return
     */
    @GetMapping("/index/getAllUsers")
    public Result getAllUser(){
        List<User> allUser = indexService.getAllUser();
        return Result.success(allUser);
    }
    /**
     * 用户分页查询
     * @return
     */
    @GetMapping("/index/getAllUsers/query")
    public Result getAllUserQuery(UserQueryDto queryDto){
        UserVo allUserQuery = indexService.getAllUserQuery(queryDto);
        return Result.success(allUserQuery);
    }

    /**
     * 保存登录用户ip
     * @param loginDto
     * @return
     */
    @PostMapping("/index/saveLoginIp")
    public Result saveLoginIp(@RequestBody LoginIpDto loginIpDto, HttpServletRequest request){
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        indexService.saveLoginUser(loginIpDto,ipAddress);
        return Result.success(200);
    }
    /**
     * 用户注册
     * @param loginDto
     * @return
     */
    @PostMapping("/index/signup")
    public Result signUp(@RequestBody SignUpDto loginDto){
        System.out.println(loginDto);
        SignUpVo signup = indexService.signup(loginDto);
        return Result.success(signup);
    }

    /**
     * 访问用户ip
     * @param ip
     */
    @GetMapping("/index/setIp")
    public void setIp(String ip){
        indexService.saveAccessUser(ip);
    }


    @PostMapping("/login")
    public Result userLogin(String username, String password, HttpSession session) {
        //创建一个条件构造器
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        //传入查询条件
        boolean login = indexService.login(username, password);
        String md5Password = md5Utils.encrypByMd5(password);
        userQueryWrapper.eq("account", username);
        userQueryWrapper.eq("password", md5Password);
        User user = indexService.getOne(userQueryWrapper);
        if (login) {
            String res = TokenUtil.sign(user.getUsername(), user.getUserId());
            //将用户名存储到session中
            return Result.success(res);
        }
        return Result.error("0");
    }

    /**
     * 根据返回的token解析用户id并返回该用户的歌单列表
     *
     * @return
     */
    @GetMapping("/getCurrentUser")
    public Result getUserFolderByUserID(HttpServletRequest  request) {
        String token = request.getHeader("token");
        String id = TokenUtil.getId(token);
        Map<String, String> map;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        User one = indexService.getOne(queryWrapper);
        return Result.success(one);
    }

    @GetMapping("/index/getUserById/{id}")
    public Result getUserById(@PathVariable String id){
        User userById = indexService.getUserById(id);
        return Result.success(userById);
    }

    @PostMapping("/index/updateImg")
    public Result updateImg(){
        System.out.println("1211");
        return null;
    }
}