package com.project.www.domain;

import lombok.*;

@Data
public class ChatMessageVO {
    private Long id;
    private String sender;
    private String content;
    private String chatRoomId;
    private String senderType; // 추가 필드: 'seller' 또는 'customer'
}
