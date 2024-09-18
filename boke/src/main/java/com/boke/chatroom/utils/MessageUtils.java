package com.boke.chatroom.utils;

import com.boke.chatroom.model.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @version v1.0
 * @ClassName: MessageUtils
 * @Description: 用来封装消息的工具类
 * @Author: 二刺猿
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,String fromName,String toName, Object message) {
        try {
            ResultMessage result = new ResultMessage();
            result.setIsSystem(isSystemMessage);
            result.setMessage(message);
            if(fromName != null) {
                result.setFromName(fromName);
            }
            if (toName != null){
                result.setToName(toName);
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

