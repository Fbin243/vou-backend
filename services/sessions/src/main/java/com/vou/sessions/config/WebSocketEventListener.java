package com.vou.sessions.config;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final static String CONNECTION_KEY = "CONNECTION";
    @NonNull
    private SimpMessagingTemplate simpMessagingTemplate;
    @NonNull
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOps;

    @PostConstruct
    public void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("Removed a web socket connection");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        log.info("Want to remove sessionId: {}", sessionId);
        if (sessionId == null) {
            throw new RuntimeException("Failed to find session id");
        }
        Integer value = (Integer) hashOps.get(sessionId, CONNECTION_KEY);
        log.info("Removed sessionId: {}, connection: {}", sessionId, value);
        if (value == null) {
            log.info("Connection key not found");
            return;
        }
        value = value - 1;
        hashOps.put(sessionId, CONNECTION_KEY, value);
        log.info("After remove {}", value);
        simpMessagingTemplate.convertAndSend("/topic/connection/" + sessionId, value);
    }
}
