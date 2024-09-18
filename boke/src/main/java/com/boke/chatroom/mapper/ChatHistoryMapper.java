package com.boke.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boke.chatroom.model.ChatHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {

    @Insert("insert into chat_history (to_name, from_name, message)values (#{toName}, #{fromName}, #{message})")
    public int insert(ChatHistory chatHistory);
}
