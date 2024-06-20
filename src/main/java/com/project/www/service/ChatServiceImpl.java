package com.project.www.service;

import com.project.www.domain.ChatMessageVO;
import com.project.www.domain.ChatRoomVO;
import com.project.www.repository.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatMapper chatMapper;

    public void createChatRoom(ChatRoomVO chatRoom) {
        chatMapper.createChatRoom(chatRoom);
    }

    public ChatRoomVO getChatRoomById(String chatRoomId) {
        return chatMapper.getChatRoomById(chatRoomId);
    }

    public void deleteChatRoomById(String chatRoomId) {
        chatMapper.deleteChatRoomById(chatRoomId);
    }

    public void insertMessage(ChatMessageVO chatMessage) {
        chatMapper.insertMessage(chatMessage);
    }

    public List<ChatMessageVO> getMessagesByRoomId(String chatRoomId) {
        return chatMapper.getMessagesByRoomId(chatRoomId);
    }

    public void deleteMessagesByRoomId(String chatRoomId) {
        chatMapper.deleteMessagesByRoomId(chatRoomId);
    }
}
