package com.project.www.controller;

import com.project.www.domain.ChatMessageVO;
import com.project.www.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageVO sendMessage(ChatMessageVO chatMessage) {
        chatService.saveMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/history/{chatRoomId}")
    @SendTo("/topic/history/{chatRoomId}")
    public List<ChatMessageVO> getHistory(@PathVariable String chatRoomId) {
        return chatService.getMessagesByChatRoomId(chatRoomId);
    }

    @GetMapping("/history/{chatRoomId}")
    public List<ChatMessageVO> getHistoryRest(@PathVariable String chatRoomId) {
        return chatService.getMessagesByChatRoomId(chatRoomId);
    }
}
