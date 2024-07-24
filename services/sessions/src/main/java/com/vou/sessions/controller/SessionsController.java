package com.vou.sessions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.sessions.dto.MessageDto;
import com.vou.sessions.dto.MessageType;
import com.vou.sessions.service.ISessionsService;
import com.vou.sessions.service.SchedulerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Controller
@AllArgsConstructor
public class SessionsController {
    private static final Logger log = LoggerFactory.getLogger(SessionsController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private SimpMessagingTemplate messagingTemplate;
    private ISessionsService sessionsService;
    private SchedulerService schedulerService;

    @MessageMapping("/game")
    public void executeGame(MessageDto message) {
        if (message.getType() == MessageType.START) {
            log.info("Start game ...");
            // Parse payload
            String playerId, sessionId;
            try {
                log.info("payload: {}", message.getPayload());
                JsonNode rootNode = objectMapper.readTree(message.getPayload());
                playerId = rootNode.get("playerId").asText();
                sessionId = rootNode.get("sessionId").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot parse payload");
            }
            sessionsService.createPlayerRecord(sessionId, playerId);
            log.info("Game started, playerId: {}", playerId);
            // Send quiz questions
            messagingTemplate.convertAndSend("/topic/start/" + playerId, sessionsService.getQuestionsBySessionId(sessionId, 20));
        }
    }

//    @PostMapping("/api/sessions")
//    public void createSession() {
//        SessionDto sessionDto = new SessionDto();
//        sessionDto.setGameId("1");
//        sessionDto.setEventId("2");
//        List<UserRecordDto> userRecordDtos = new ArrayList<>();
//        userRecordDtos.add(new UserRecordDto("1", 0, 0));
//        userRecordDtos.add(new UserRecordDto("1", 0, 0));
//        userRecordDtos.add(new UserRecordDto("1", 0, 0));
//        userRecordDtos.add(new UserRecordDto("1", 0, 0));
//        sessionDto.setUsers(userRecordDtos);
//        sessionsService.createSession(sessionDto);
//    }

    @MessageMapping("/connection")
    public void getNumberOfConnections(String sessionId, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Get number of connections ... ");
        headerAccessor.getSessionAttributes().put("sessionId", sessionId);
        messagingTemplate.convertAndSend("/topic/connection/" + sessionId, sessionsService.getNumberOfConnectionBySessionId(sessionId));
    }

    @PostMapping("/api/start")
    public void startNewSession(@RequestBody String cronExpression) {
        log.info(cronExpression);
        Runnable runnable = () -> {
            updateTime("669fedc17ada690bd952c606");
        };
        schedulerService.initializeNewTask(cronExpression, runnable);
    }

    private void updateTime(String sessionId) {
        log.info("Time: {}", Instant.now().getEpochSecond());
        messagingTemplate.convertAndSend("/topic/time/" + sessionId, Instant.now().getEpochSecond());
    }
}
