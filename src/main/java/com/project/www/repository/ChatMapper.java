package com.project.www.repository;

import com.project.www.domain.ChatMessageVO;
import com.project.www.domain.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    void createChatRoom(ChatRoomVO chatRoom);

    ChatRoomVO getChatRoomById(String chatRoomId);

    void deleteChatRoomById(String chatRoomId);

    void insertMessage(ChatMessageVO chatMessage);

    List<ChatMessageVO> getMessagesByRoomId(String chatRoomId);

    void deleteMessagesByRoomId(String chatRoomId);
}
