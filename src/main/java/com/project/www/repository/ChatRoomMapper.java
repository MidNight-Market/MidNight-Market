package com.project.www.repository;

import com.project.www.domain.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    void insertChatRoom(ChatRoomVO chatRoom);
    List<ChatRoomVO> getChatRoomsByCustomerId(@Param("customerId") String customerId);
    List<ChatRoomVO> getChatRoomsBySellerId(@Param("sellerId") String sellerId);
    void deleteChatRoomById(@Param("id") Long id);
}
