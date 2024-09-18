package com.boke.admin.service.serviceImpl;

import com.boke.admin.mapper.AdminMapper;
import com.boke.admin.service.AdminService;
import com.boke.index.mapper.AccessRecordMapper;
import com.boke.index.mapper.LoginRecordMapper;
import com.boke.index.mapper.UserMapper;
import com.boke.index.model.AccessRecord;
import com.boke.index.model.LoginRecord;
import com.boke.index.model.User;
import com.boke.index.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Autowired
    private AccessRecordMapper accessRecordMapper;
//    @Autowired
//    PasswordEncoder passwordEncoder;
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @Override
    public Result deleteUser(String id) {
        if(id.equals(null)){
            return Result.error("id不存在");
        }
        int i = adminMapper.deleteByUserId(id);
        if(i>0){
            return Result.success("用户删除成功");
        }
        else{
            return Result.error("用户删除失败");
        }
    }
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public Result updateUser(User user) {
        if(user==null){
            return Result.error("-1");
        }
        User userById = userMapper.selectUserById(user.getUserId());
        BeanUtils.copyProperties(user,userById);
        //密码加密BCRYPT
//        String encodePassword = passwordEncoder.encode(user.getPassWord());
//        userById.setPassWord(user);

        if(userById.getUserId() == null || userById.getUsername() == null
                || userById.getAccount() == null || userById.getSex() == null ||
                    userById.getPassword() == null){
            return Result.error("-1");
        }
        int i = adminMapper.adminUpdateUser(userById);
        if(i <= 0){
            return Result.error("-1");
        }
        return Result.success(true);
    }

    @Override
    public List<LoginRecord> getLoginRecord() {
        List<LoginRecord> loginRecord = loginRecordMapper.getLoginRecord();
        return loginRecord;
    }

    @Override
    public List<AccessRecord> getAccessRecord() {
        List<AccessRecord> accessRecord = accessRecordMapper.getAccessRecord();
        return accessRecord;
    }
}
