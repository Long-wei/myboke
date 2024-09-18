package com.boke.admin.controller;

import com.boke.admin.service.AdminService;
import com.boke.index.model.AccessRecord;
import com.boke.index.model.LoginRecord;
import com.boke.index.model.User;
import com.boke.index.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("${cros.url}")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("admin/deleteUser")
    public Result deleteUser(String id){
        Result result = adminService.deleteUser(id);
        return result;
    }
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("admin/updateUser")
    public Result updateUser(@RequestBody User user){
        Result result = adminService.updateUser(user);
        return result;
    }

    /**
     * 获得登录用户记录
     * @return
     */
    @GetMapping("admin/getLoginRecord")
    public Result getLoginRecord(){
        List<LoginRecord> loginRecord = adminService.getLoginRecord();
        return Result.success(loginRecord);
    }

    /**
     * 获得访问记录
     * @return
     */
    @GetMapping("admin/getAccessRecord")
    public Result getAccessRecord(){
        List<AccessRecord> accessRecord = adminService.getAccessRecord();
        return Result.success(accessRecord);
    }
}
