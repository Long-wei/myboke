package com.boke.chatroom.config;

import com.boke.chatroom.model.Message;
import com.boke.chatroom.utils.MessageUtils;
import com.boke.chatroom.utils.TuringApiCaller;
import com.boke.index.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//socket
@Component
@ServerEndpoint(value = "/chat/{token}")
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static Map<String,ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();
    private String userId;
    private Session session;
    private static TuringApiCaller turingApiCaller = new TuringApiCaller();
    @OnOpen
    //连接建立成功调用
    public void onOpen(Session session, @PathParam("token") String token) {
        if(token == null)
        {
            return;
        }
        try {
            this.session = session;
            String id = TokenUtil.getId(token);
            this.userId = id;
            onlineUsers.put(id, this);
            String message = MessageUtils.getMessage(true, null, null, getNames());
            broadcastAllUsers(message);
        }catch (Exception e){}
    }

    private void broadcastAllUsers(String message) {
        try {
        Set<String> names = onlineUsers.keySet();
        for(String name : names){
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            chatEndpoint.session.getBasicRemote().sendText(message);
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getNames() {
        return onlineUsers.keySet();
    }

    @OnMessage
    //接收到消息时调用
    public void onMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            String userId = TokenUtil.getId(mess.getToken());
            if(mess.getToName().equals("0")){
                String returnMessage = turingApiCaller.start(mess.getMessage());
                String data = MessageUtils.getMessage(false, "0", userId, returnMessage);
                ChatEndpoint chatEndpoint = onlineUsers.get(userId);
                chatEndpoint.session.getBasicRemote().sendText(data);
            }
            else if(mess.getToName()==""){
                String data = MessageUtils.getMessage(false, userId, mess.getToName(), mess.getMessage());
                broadcastAllUsers(data);
            }
            else {
                String data = MessageUtils.getMessage(false, userId, mess.getToName(), mess.getMessage());
                ChatEndpoint chatEndpoint = onlineUsers.get(mess.getToName());
                chatEndpoint.session.getBasicRemote().sendText(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    //连接关闭时调用
    public void onClose(Session session) {
        try{
            onlineUsers.remove(userId);
            String message = MessageUtils.getMessage(true, null, null, getNames());
            broadcastAllUsers(message);
        }catch (Exception e){}
    }

    public static Map<String, ChatEndpoint> getOnlineUsers() {
        return onlineUsers;
    }

    public static void setOnlineUsers(Map<String, ChatEndpoint> onlineUsers) {
        ChatEndpoint.onlineUsers = onlineUsers;
    }
}

