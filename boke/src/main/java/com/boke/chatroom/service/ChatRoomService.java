package com.boke.chatroom.service;

import com.boke.chatroom.dto.GetFriend;
import com.boke.chatroom.dto.MessageUserDto;
import com.boke.chatroom.dto.ToNameAndFromNameDto;
import com.boke.chatroom.model.ChatHistory;
import com.boke.chatroom.model.Friend;
import com.boke.chatroom.vo.NameIdVo;
import com.boke.chatroom.vo.SearchUserVo;
import com.boke.index.model.User;

import java.util.List;

public interface ChatRoomService {
    /**
     * 获取toName->用户
     * @param token
     * @return
     */
    public User getUser(String token);

    /**
     * 获得在线用户名和id
     * @return
     */
    public List<NameIdVo> getUserOnline();

    /**
     * 通过请求头携带的token获得当前登录用户id
     * @param token
     * @return
     */
    public String getCurrentUserId(String token);

    /**
     * 保存历史聊天记录
     * @param chatHistory
     * @return
     */
    public boolean saveHistoryChat(ChatHistory chatHistory);

    /**
     * 恢复聊天记录
     * @param fromName
     * @param toName
     * @return
     */
    public List<ChatHistory> reHistoryChat(ToNameAndFromNameDto toNameAndFromNameDto);

    /**
     * 模糊搜索用户
     * @param messageUserDto
     * @return
     */
    public List<SearchUserVo> getUserByLike(MessageUserDto messageUserDto);


    /**
     * 通过id查询好友是否在线
     * @param id
     * @return
     */
    public List<String> getOnlineUserById(List<String> ids);

    /**
     * 添加好友
     * @param id
     * @return
     */
    public int addFriend(String id, String token);

    /**
     * 查看指定用户是否存在指定好友
     * @param getFriend
     * @return
     */
    public boolean getFriend(GetFriend getFriend);

    /**
     * 获得用户好友
     * @param id
     * @return
     */
    public List<User> getUserFriend(String id);
}

