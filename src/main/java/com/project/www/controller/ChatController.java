package com.project.www.controller;

import com.project.www.domain.ChatRoomVO;
import com.project.www.domain.MessageVO;
import com.project.www.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // 채팅방 목록을 보여주는 페이지로 이동
    @GetMapping("/rooms")
    public String getChatRooms(Model model, Principal principal) {
        String userId = "test";
        List<ChatRoomVO> chatRooms = chatService.getChatRoomsByCustomerId(userId);
        chatRooms.addAll(chatService.getChatRoomsBySellerId(userId));
        model.addAttribute("chatRooms", chatRooms);
        return "chat/rooms"; // rooms.html 파일을 반환
    }

    // 특정 채팅방을 보여주는 페이지로 이동
    @GetMapping("/room/{roomId}")
    public String getChatRoom(@PathVariable Long roomId, Model model) {
        List<MessageVO> messages = chatService.getMessagesByChatRoomId(roomId);
        model.addAttribute("messages", messages);
        model.addAttribute("roomId", roomId);
        return "chat/room"; // room.html 파일을 반환
    }

    // 새로운 채팅방을 만드는 페이지로 이동 (폼을 보여주는 페이지)
    @GetMapping("/new-room")
    public String createChatRoomForm() {
        return "chat/new-room"; // new-room.html 파일을 반환
    }

    // 실제로 새로운 채팅방을 만드는 요청을 처리
    @PostMapping("/room")
    public String createChatRoom(@RequestParam String customerId, @RequestParam String sellerId) {
        chatService.createChatRoom(customerId, sellerId);
        return "redirect:/chat/rooms";
    }

    // 메시지를 보내는 요청을 처리
    @PostMapping("/send")
    public String sendMessage(@ModelAttribute MessageVO message) {
        log.info("여기로들어옴{}",message);
        message.setReceiverId("test1");
        message.setSenderId("test");
        message.setChatRoomId(1L);
        chatService.sendMessage(message);
        return "redirect:/chat/room/" + message.getChatRoomId();
    }
}