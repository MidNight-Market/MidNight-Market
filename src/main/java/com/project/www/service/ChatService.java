package com.project.www.service;

import com.project.www.domain.ChatMessageVO;
import com.project.www.domain.ChatRoomVO;

import java.util.List;

public interface ChatService {
    void createChatRoom(ChatRoomVO chatRoom);

    ChatRoomVO getChatRoomById(String chatRoomId);

    void deleteChatRoomById(String chatRoomId);

    List<ChatMessageVO> getMessagesByRoomId(String chatRoomId);

    void deleteMessagesByRoomId(String chatRoomId);

    void insertMessage(ChatMessageVO chatMessage);
}
