package com.project.www.handler;

import com.project.www.domain.MessageVO;
import com.project.www.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트와 연결이 성립되었을 때 실행되는 메서드
        String senderId = "test";
        if (senderId != null) {
            sessions.put(senderId, session);
            session.getAttributes().put("senderId", senderId); // 세션 속성에 senderId 저장
        } else {

        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지를 처리하는 로직
        log.info("여기로오나{}",message.getPayload());
        String senderId = "test";
        if (senderId != null) {
            sendMessageToRoom(senderId, message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트와 연결이 종료될 때 실행되는 메서드
        String senderId = "test";
        if (senderId != null) {
            sessions.remove(senderId);
        }
    }

    private String extractSenderId(WebSocketSession session) {
        // 실제로는 클라이언트로부터 받아올 수 있는 senderId 추출 로직을 구현해야 합니다.
        // 예를 들어, HTTP 요청에서 세션으로부터 가져오거나, WebSocket 메시지의 파라미터로부터 추출할 수 있습니다.
        // 이 예시에서는 간단하게 세션 아이디를 senderId로 사용하는 예시를 보여줍니다.
        return session.getId();
    }

    private void sendMessageToRoom(String senderId, String message) {

    }
}