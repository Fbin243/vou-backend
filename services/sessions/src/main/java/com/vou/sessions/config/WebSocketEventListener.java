package com.vou.sessions.config;

import com.vou.sessions.engine.GameEngine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final static String CONNECTION_KEY = "CONNECTION";
    private final GameEngine gameEngine;
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("Removed a web socket connection");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        String playerId = headerAccessor.getSessionAttributes().get("playerId").toString();
        log.info("Want to remove sessionId, playerId: {} {}", sessionId, playerId);
        simpMessagingTemplate.convertAndSend("/topic/connection/" + sessionId, gameEngine.disconnect(sessionId, playerId));
    }
}
