package com.boke.index.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boke.chatroom.vo.SearchUserVo;
import com.boke.index.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User>{
    /**
     * 根据查询用户
     * @param user_id
     * @return
     */
    @Select("select * from user where user_id= #{user_id}")
    public User selectUserById(String user_id);
    /**
     * 获得用户全部信息
     * @return
     */
    @Select("select user_id,username,age,sex,account,password from user")
    public List<User> getAllUser();
    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Select("select account,password from user where account=#{account}")
    public User selectUserByAccount(String account);

    /**
     * 新用户注册插入数据库
     * @param user
     */
    @Update("insert into user(username,password,age,sex,account)" +
            " values" +
            " (#{username},#{password},#{age},#{sex},#{account})")
    public void createUser(User user);

    @Select("select * from user where account=#{account}")
    public User selectUserAllByAccount(String account);

    public int updateUser(User user);

    @Select("select * from user where account like concat('%',#{messageUser},'%') or username like concat('%',#{messageUser},'%')")
    public List<SearchUserVo> getUserByLike(String messageUser);
}
