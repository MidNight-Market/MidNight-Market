package com.project.www.service;

import com.project.www.domain.ChatMessageVO;

import java.util.List;

public interface ChatService {
    void saveMessage(ChatMessageVO message);

    List<ChatMessageVO> getMessagesByChatRoomId(String chatRoomId);
}
