package com.project.www.repository;

import com.project.www.domain.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    void insertMessage(MessageVO message);
    List<MessageVO> getMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);
    void deleteMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}