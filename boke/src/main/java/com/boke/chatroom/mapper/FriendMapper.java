package com.boke.chatroom.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boke.chatroom.model.Friend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    @Insert("insert into list_friend (user_id, friend_id)values (#{userId},#{friendId})")
    public int insert(Friend friend);
}
