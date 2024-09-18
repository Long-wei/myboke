package com.boke.chatroom.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boke.chatroom.config.ChatEndpoint;
import com.boke.chatroom.dto.GetFriend;
import com.boke.chatroom.dto.MessageUserDto;
import com.boke.chatroom.dto.ToNameAndFromNameDto;
import com.boke.chatroom.mapper.ChatHistoryMapper;
import com.boke.chatroom.mapper.FriendMapper;
import com.boke.chatroom.model.ChatHistory;
import com.boke.chatroom.model.Friend;
import com.boke.chatroom.service.ChatRoomService;
import com.boke.chatroom.vo.NameIdVo;
import com.boke.chatroom.vo.SearchUserVo;
import com.boke.index.mapper.UserMapper;
import com.boke.index.model.User;
import com.boke.index.utils.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChatHistoryMapper chatHistoryMapper;
    @Autowired
    private FriendMapper friendMapper;
    /**
     * 通过token获取用户
     * @param token
     * @return
     */
    @Override
    public User getUser(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String id = TokenUtil.getId(token);
        queryWrapper.eq("user_id",id);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 获得当前在线用户列表
     * @return
     */
    @Override
    public List<NameIdVo> getUserOnline() {
        Map<String, ChatEndpoint> onlineUsers = ChatEndpoint.getOnlineUsers();
        Set<String> names = onlineUsers.keySet();
        List<NameIdVo> users = new ArrayList<>();
        for(String name : names) {
            User user = userMapper.selectUserById(name);
            users.add(new NameIdVo(user.getUserId(), user.getUsername()));
        }
        return users;
    }

    /**
     * 获得当前在线用户id
     * @param token
     * @return
     */
    @Override
    public String getCurrentUserId(String token) {
        String id = TokenUtil.getId(token);
        return id;
    }

    /**
     * 保存聊天记录
     * @param chatHistory
     * @return
     */
    @Override
    public boolean saveHistoryChat(ChatHistory chatHistory) {
        if(chatHistory.getFromName()==null && chatHistory.getToName()==null){
            return false;
        }
        if(chatHistory.getToName() == ""){
            chatHistory.setToName("-1");
            int i = chatHistoryMapper.insert(chatHistory);
            return true;
        }else {
            int i = chatHistoryMapper.insert(chatHistory);
            return true;
        }
    }

    @Override
    public List<ChatHistory> reHistoryChat(ToNameAndFromNameDto toNameAndFromNameDto) {
        if(toNameAndFromNameDto.getToName().equals("-1")){
            QueryWrapper<ChatHistory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("to_name", "-1");
            List<ChatHistory> chatHistories = chatHistoryMapper.selectList(queryWrapper);
            return chatHistories;
        }
        else{
            QueryWrapper<ChatHistory> queryWrapper = new QueryWrapper<>();
            QueryWrapper<ChatHistory> queryWrapper1 = new QueryWrapper<>();
            queryWrapper.eq("from_name", toNameAndFromNameDto.getFromName());
            queryWrapper.eq("to_name", toNameAndFromNameDto.getToName());
            queryWrapper1.eq("from_name", toNameAndFromNameDto.getToName());
            queryWrapper1.eq("to_name", toNameAndFromNameDto.getFromName());
            List<ChatHistory> chatHistories = chatHistoryMapper.selectList(queryWrapper);
            List<ChatHistory> chatHistories1 = chatHistoryMapper.selectList(queryWrapper1);
            chatHistories.addAll(chatHistories1);
            Collections.sort(chatHistories, new Comparator<ChatHistory>() {
                @Override
                public int compare(ChatHistory o1, ChatHistory o2) {
                    return Integer.parseInt(o1.getId())-Integer.parseInt(o2.getId());
                }
            });
            return chatHistories;
        }
    }

    @Override
    public List<SearchUserVo> getUserByLike(MessageUserDto messageUserDto) {
        List<SearchUserVo> userByLike = userMapper.getUserByLike(messageUserDto.getMessageUser());
        for(SearchUserVo user : userByLike){
            Map<String, ChatEndpoint> onlineUsers = ChatEndpoint.getOnlineUsers();
            ChatEndpoint chatEndpoint = onlineUsers.get(user.getUserId());
            if(chatEndpoint != null){
                user.setOnlineStatus(true);
            }
        }
        return userByLike;
    }

    @Override
    public List<String> getOnlineUserById(List<String> ids) {
        if(ids==null){
            return null;
        }
        Map<String, ChatEndpoint> onlineUsers = ChatEndpoint.getOnlineUsers();
        //Map<用户id,用户名>
        List<String> stringChatEndpointMap = null;
        for(String id : ids){
            ChatEndpoint chatEndpoint = onlineUsers.get(id);
            if(chatEndpoint != null){
                stringChatEndpointMap.add(id);
            }
        }
        return stringChatEndpointMap;
    }

    /**
     * 添加好友
     * @param id 要添加好友的id
     * @param token
     * @return
     */
    @Override
    public int addFriend(String id, String token) {
        if(id == null || token ==null){
            return 0;
        }
        Friend friend = null;
        Friend friend1 = null;
        String id1 = TokenUtil.getId(token);
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Object> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("user_id", id1);
        queryWrapper.eq("friend_id", id);
        queryWrapper1.eq("user_id", id);
        queryWrapper1.eq("friend_id", id1);
        friend = friendMapper.selectOne(queryWrapper);
        friend1 = friendMapper.selectOne(queryWrapper);
        if(friend != null || friend1 != null){
            return 0;
        }
        friend = new Friend();
        friend.setUserId(id1);
        friend.setFriendId(id);
        friend1 = new Friend();
        friend1.setUserId(id);
        friend1.setFriendId(id1);
        int insert = friendMapper.insert(friend);
        friendMapper.insert(friend1);
        return insert;
    }

    /**
     * 查看指定用户是否存在指定好友
     * @param getFriend
     * @return
     */
    @Override
    public boolean getFriend(GetFriend getFriend) {
        if(getFriend.getFriendId()==null || getFriend.getId()==null){
            return false;
        }
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", getFriend.getId());
        queryWrapper.eq("friend_id", getFriend.getFriendId());
        Friend friend = friendMapper.selectOne(queryWrapper);
        QueryWrapper<Friend> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", getFriend.getFriendId());
        queryWrapper1.eq("friend_id", getFriend.getId());
        Friend friend1 = friendMapper.selectOne(queryWrapper1);
        if(friend !=null || friend1 != null){
            return true;
        }
        return false;
    }

    /**
     * 获得用户好友
     * @param id
     * @return
     */
    @Override
    public List<User> getUserFriend(String id) {
        if(id==null){
            return null;
        }
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<Friend> friends = friendMapper.selectList(queryWrapper);
        List<User> userList = new ArrayList<>();
        for(Friend friend : friends){
            User user = userMapper.selectUserById(friend.getFriendId());
            userList.add(user);
        }
        return userList;
    }
}
