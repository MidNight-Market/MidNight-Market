package com.project.www.repository;

import com.project.www.domain.ChatMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatMessageMapper {
    void saveMessage(ChatMessageVO message);

    List<ChatMessageVO> getMessagesByChatRoomId(@Param("chatRoomId") String chatRoomId);
}
