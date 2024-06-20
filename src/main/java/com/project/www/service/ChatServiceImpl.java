package com.project.www.service;

import com.project.www.domain.ChatRoomVO;
import com.project.www.domain.MessageVO;
import com.project.www.repository.ChatRoomMapper;
import com.project.www.repository.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional
    public void createChatRoom(String customerId, String sellerId) {
        ChatRoomVO chatRoom = new ChatRoomVO();
        chatRoom.setCustomerId(customerId);
        chatRoom.setSellerId(sellerId);
        chatRoomMapper.insertChatRoom(chatRoom);
    }

    @Override
    public List<ChatRoomVO> getChatRoomsByCustomerId(String customerId) {
        return chatRoomMapper.getChatRoomsByCustomerId(customerId);
    }

    @Override
    public List<ChatRoomVO> getChatRoomsBySellerId(String sellerId) {
        return chatRoomMapper.getChatRoomsBySellerId(sellerId);
    }

    @Override
    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        messageMapper.deleteMessagesByChatRoomId(chatRoomId);
        chatRoomMapper.deleteChatRoomById(chatRoomId);
    }

    @Override
    public void sendMessage(MessageVO message) {
        message.setReceiverId("test1");
        message.setSenderId("test");
        messageMapper.insertMessage(message);
    }

    @Override
    public List<MessageVO> getMessagesByChatRoomId(Long chatRoomId) {
        return messageMapper.getMessagesByChatRoomId(chatRoomId);
    }
}
