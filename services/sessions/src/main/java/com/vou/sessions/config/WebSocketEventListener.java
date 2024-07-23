package com.vou.sessions.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final static String KEY_CONNECTION = "CONNECTION";
    private StringRedisTemplate stringRedisTemplate;
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
        String value = stringRedisTemplate.opsForValue().get(KEY_CONNECTION);
        if (value == null) {
            stringRedisTemplate.opsForValue().set(KEY_CONNECTION, "1");
            value = "1";
        } else stringRedisTemplate.opsForValue().increment(KEY_CONNECTION);
        simpMessagingTemplate.convertAndSend("/topic/connection", Integer.parseInt(value) + 1);
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("Removed a web socket connection");
        String value = stringRedisTemplate.opsForValue().get(KEY_CONNECTION);
        stringRedisTemplate.opsForValue().decrement(KEY_CONNECTION);
        simpMessagingTemplate.convertAndSend("/topic/connection", Integer.parseInt(value) - 1);
    }
}
