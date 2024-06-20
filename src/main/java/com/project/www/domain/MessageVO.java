package com.project.www.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVO {
    private Long id;
    private Long chatRoomId;
    private String senderId;
    private String receiverId;
    private String content;
    private Date timestamp;
}