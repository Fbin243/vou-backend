package com.vou.sessions.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vou.sessions.dto.RecordDto;
import com.vou.sessions.dto.quizgame.QuizRecordDto;
import com.vou.sessions.service.SessionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class GameEngine {
    protected static final String CONNECTION_KEY = "CONNECTION";
    protected static final Logger log = LoggerFactory.getLogger(SessionsService.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static final ObjectNode objectNode = objectMapper.createObjectNode();
    protected RedisTemplate<String, Object> redisTemplate;
    protected HashOperations<String, String, Object> hashOps;

    public Object connect(String sessionId) {
        Integer value = (Integer) hashOps.get(sessionId, CONNECTION_KEY);
        if (value == null) {
            hashOps.put(sessionId, CONNECTION_KEY, 1);
        } else {
            value = value + 1;
            hashOps.put(sessionId, CONNECTION_KEY, value);
        }

        log.info("Connection: {}", value);

        return value == null ? 1 : value;
    }

    public Object disconnect(String sessionId, String playerId) {
        Integer value = (Integer) hashOps.get(sessionId, CONNECTION_KEY);
        if (value == null) {
            log.info("Failed to get connections of sessionId: {}", sessionId);
        } else {
            value = value - 1;
            hashOps.put(sessionId, CONNECTION_KEY, value);
        }
        this.updateTotalTime(sessionId, playerId);
        return value == null ? 1 : value;
    }

    public abstract void setUp(String sessionId);

    public abstract Object start(String sessionId, String playerId);

    public abstract void update(String sessionId, String playerId, Object update);

    public abstract List<RecordDto> end(String sessionId);

    protected abstract void updateTotalTime(String sessionId, String playerId);
}
