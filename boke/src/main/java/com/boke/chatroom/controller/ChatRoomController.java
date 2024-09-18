package com.boke.chatroom.controller;

import com.boke.chatroom.dto.GetFriend;
import com.boke.chatroom.dto.MessageUserDto;
import com.boke.chatroom.dto.ToNameAndFromNameDto;
import com.boke.chatroom.model.ChatHistory;
import com.boke.chatroom.model.Friend;
import com.boke.chatroom.model.Id;
import com.boke.chatroom.service.ChatRoomService;
import com.boke.chatroom.utils.TuringApiCaller;
import com.boke.chatroom.vo.NameIdVo;
import com.boke.chatroom.vo.SearchUserVo;
import com.boke.index.model.User;
import com.boke.index.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private TuringApiCaller turingApiCaller;
    /**
     * 获取toName->用户
     * @param session
     * @return
     */
    @RequestMapping("/chatroom/getUserId")
    public Result getUser(HttpServletRequest session) {
        String token = session.getHeader("token");
        User user = chatRoomService.getUser(token);
        if(user == null){
            return Result.error("0");
        }
        return Result.success(user.getUserId());
    }

    /**
     * 获得在线用户名和id
     * @return
     */
    @GetMapping("/chatroom/getOnlineUser")
    public Result getUserOnline(){
        List<NameIdVo> userOnline = chatRoomService.getUserOnline();
        return Result.success(userOnline);
    }

    /**
     * 查询当前用户id
     * @param request
     * @return
     */
    @GetMapping("/chatroom/getCurrentUserId")
    public Result getCurrentUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        String currentUserId = chatRoomService.getCurrentUserId(token);
        if(currentUserId == null){
            return Result.success("0");
        }
        return Result.success(currentUserId);
    }

    /**
     * 保存聊天记录
     * @param chatHistory
     * @return
     */
    @PostMapping("/chatroom/saveHistoryChat")
    public Result saveHistoryChat(@RequestBody ChatHistory chatHistory){
        boolean b = chatRoomService.saveHistoryChat(chatHistory);
        return Result.success(b);
    }

    /**
     * 恢复聊天记录
     * @return
     */
    @PostMapping("/chatroom/reHistoryChat")
    public Result reHistoryChat(@RequestBody ToNameAndFromNameDto toNameAndFromNameDto){
        List<ChatHistory> chatHistories = chatRoomService.reHistoryChat(toNameAndFromNameDto);
        if(chatHistories==null){
            return Result.success(0);
        }
        return Result.success(chatHistories);
    }

    /**
     * 搜索好友模糊查询接口
     * @param userMessage
     * @return
     */
    @PostMapping("/chatroom/getUser")
    public Result getUserByLike(@RequestBody MessageUserDto messageUserDto){
        List<SearchUserVo> userByLike = chatRoomService.getUserByLike(messageUserDto);
        return Result.success(userByLike);
    }

    /**
     * 通过id查询用户是否在线
     * @param ids
     * @return
     */
    @PostMapping("/chatroom/getOnlineUserById")
    public Result getOnlineUserById(List<String> ids){
        System.out.println(ids);
        return null;
    }

    /**
     * 添加好友
     * @param id
     * @return
     */
    @PostMapping("/chatroom/addFriend")
    public Result addFriend(@RequestBody Id id, HttpServletRequest request){
        String token = request.getHeader("token");
        int i = chatRoomService.addFriend(id.getId(), token);
        if(i <= 0){
            return Result.success(false);
        }
        return Result.success(true);
    }

    /**
     * 查看指定用户是否存在指定好友
     * @param getFriend
     * @return
     */
    @PostMapping("/chatroom/getFriend")
    public Result getFriend(@RequestBody GetFriend getFriend){
        boolean friend = chatRoomService.getFriend(getFriend);
        //  好友-----true
        //  不是好友--fasle
        if(friend){
            return Result.success(true);
        }
        return Result.success(false);
    }

    /**
     * 获得用户好友
     * @param id
     * @return
     */
    @GetMapping("/chatroom/getUserFriend/{id}")
    public Result getUserFriend(@PathVariable String id){
        List<User> userFriend = chatRoomService.getUserFriend(id);
        if(userFriend==null){
            return Result.success(null);
        }
        return Result.success(userFriend);
    }
}