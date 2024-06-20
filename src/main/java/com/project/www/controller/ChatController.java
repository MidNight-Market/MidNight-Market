package com.project.www.controller;

import com.project.www.domain.ChatMessageVO;
import com.project.www.service.ChatService;
import com.project.www.domain.ChatRoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/room")
    @ResponseBody
    public void createChatRoom(@RequestBody ChatRoomVO chatRoom) {
        chatService.createChatRoom(chatRoom);
    }

    @GetMapping("/room/{chatRoomId}")
    @ResponseBody
    public ChatRoomVO getChatRoomById(@PathVariable String chatRoomId) {
        return chatService.getChatRoomById(chatRoomId);
    }

    @DeleteMapping("/room/{chatRoomId}")
    @ResponseBody
    public void deleteChatRoomById(@PathVariable String chatRoomId) {
        chatService.deleteChatRoomById(chatRoomId);
    }

    @GetMapping("/messages/{chatRoomId}")
    @ResponseBody
    public List<ChatMessageVO> getMessagesByRoomId(@PathVariable String chatRoomId) {
        return chatService.getMessagesByRoomId(chatRoomId);
    }

    @DeleteMapping("/messages/{chatRoomId}")
    @ResponseBody
    public void deleteMessagesByRoomId(@PathVariable String chatRoomId) {
        chatService.deleteMessagesByRoomId(chatRoomId);
    }

    @GetMapping("/page")
    public String chatPage() {
        return "chat";  // templates/chat.html로 이동
    }
}
