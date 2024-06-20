package com.project.www.handler;

import com.project.www.domain.ChatMessageVO;
import com.project.www.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatHandler {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageVO sendMessage(ChatMessageVO chatMessage) {
        chatService.insertMessage(chatMessage); // 메시지 저장
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageVO addUser(ChatMessageVO chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " joined!");
        return chatMessage;
    }
}