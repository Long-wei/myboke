package com.boke.admin.mapper;

import com.boke.index.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    /**
     * 根据id删除用户
     * @param user_id
     * @return
     */
    @Delete("delete from user where user_id=#{user_id}")
    public int deleteByUserId(String user_id);
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Update(
            "update user set username=#{username},password=#{password},age=#{age},sex=#{sex},account=#{account}" +
                    " where user_id=#{userId}")
    public int adminUpdateUser(User user);
}
