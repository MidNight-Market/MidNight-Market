package com.project.www.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomVO {
    private Long id;
    private String customerId;
    private String sellerId;
    private Date createdAt;
}