package com.vou.sessions.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.sessions.dto.MessageDto;
import com.vou.sessions.dto.MessageType;
import com.vou.sessions.dto.quizgame.PlayerStats;
import com.vou.sessions.service.ISessionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;

@Controller
public class SessionsController {
    private static final Logger log = LoggerFactory.getLogger(SessionsController.class);
    private static final String sessionId = "session_id";
    private final static String KEY_CONNECTION = "CONNECTION";
    private static int currentConnection = 0;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, PlayerStats> hashOps;
    private SimpMessagingTemplate messagingTemplate;
    private ISessionsService sessionsService;
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    public SessionsController(RedisTemplate<String, Object> redisTemplate, SimpMessagingTemplate messagingTemplate, ISessionsService sessionsService, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.messagingTemplate = messagingTemplate;
        this.sessionsService = sessionsService;
        this.hashOps = redisTemplate.opsForHash();
    }

    @MessageMapping("/start")
    public void startGame(MessageDto message) {
        log.info("start game ... ");
        if (message.getType() == MessageType.START) {
            // Check user id is new or not
            String playerId, eventId, gameId;
            try {
                log.info("payload: {}", message.getPayload());
                JsonNode rootNode = objectMapper.readTree(message.getPayload());
                log.info("Root node: {}", rootNode);
                playerId = rootNode.get("playerId").asText();
                eventId = rootNode.get("eventId").asText();
                gameId = rootNode.get("gameId").asText();
                log.info("Game session: {}, {}, {}", playerId, eventId, gameId);
                PlayerStats playerStats = hashOps.get(sessionId, playerId);
                if (playerStats == null) {
                    playerStats = new PlayerStats(0, 0);
                    hashOps.put(sessionId, playerId, playerStats);
                }

                // Send quiz questions
                messagingTemplate.convertAndSend("/topic/start/" + playerId, sessionsService.getQuestions(20));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/api/sessions")
    public void createSession() {
        sessionsService.createSession();
    }

    @MessageMapping("/update")
    public void updateGame(MessageDto message) {
        log.info("update game ... ");
    }

    @MessageMapping("/connection")
    public void getNumberOfConnections() {
        log.info("getNumberOfConnections ... ");
        int numberOfConnection = Integer.parseInt(stringRedisTemplate.opsForValue().get(KEY_CONNECTION));
        // Send number of active players
        messagingTemplate.convertAndSend("/topic/connection", numberOfConnection);
    }

    @Scheduled(fixedRate = 1000)
    public void updateTime() {
        messagingTemplate.convertAndSend("/topic/time", Instant.now().getEpochSecond());
    }
}
