package com.project.www.service;

import com.project.www.domain.ChatRoomVO;
import com.project.www.domain.MessageVO;

import java.util.List;

public interface ChatService {
    void createChatRoom(String customerId, String sellerId);
    List<ChatRoomVO> getChatRoomsByCustomerId(String customerId);
    List<ChatRoomVO> getChatRoomsBySellerId(String sellerId);
    void deleteChatRoom(Long chatRoomId);
    void sendMessage(MessageVO message);
    List<MessageVO> getMessagesByChatRoomId(Long chatRoomId);
}