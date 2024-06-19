package com.project.www.service;

import com.project.www.domain.ChatMessageVO;
import com.project.www.repository.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public void saveMessage(ChatMessageVO message) {
        message.setTimestamp(LocalDateTime.now());
        chatMessageMapper.saveMessage(message);
    }

    @Override
    public List<ChatMessageVO> getMessagesByChatRoomId(String chatRoomId) {
        return chatMessageMapper.getMessagesByChatRoomId(chatRoomId);
    }
}
