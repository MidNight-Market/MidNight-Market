package com.project.www.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageVO {
    private Long id;
    private String chatRoomId;
    private String senderId;
    private String message;
    private LocalDateTime timestamp;
}
