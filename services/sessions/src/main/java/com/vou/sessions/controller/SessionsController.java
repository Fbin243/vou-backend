package com.vou.sessions.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.sessions.consumer.KafkaConsumerService;
import com.vou.sessions.dto.MessageDto;
import com.vou.sessions.dto.SetUpSessionDto;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.model.EventSessionInfo;
import com.vou.sessions.schedule.SchedulerService;
import com.vou.sessions.service.ISessionsService;
import com.vou.sessions.utils.Utils;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class SessionsController {
    private KafkaConsumerService kafkaConsumerService;
    private static final Logger log = LoggerFactory.getLogger(SessionsController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private SimpMessagingTemplate messagingTemplate;
    private ISessionsService sessionsService;
    private SchedulerService schedulerService;
    private GameEngine gameEngine;

    @MessageMapping("/game")
    public void executeGame(MessageDto message, SimpMessageHeaderAccessor headerAccessor) {
        String playerId, sessionId;
        JsonNode reqNode;

        try {
            log.info("payload: {}", message.getPayload());
            reqNode = objectMapper.readTree(message.getPayload());
            playerId = reqNode.get("playerId").asText();
            sessionId = reqNode.get("sessionId").asText();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot parse payload");
        }

        switch (message.getType()) {
            case CONNECTION:
                log.info("Connect game ...");
                headerAccessor.getSessionAttributes().put("sessionId", sessionId);
                headerAccessor.getSessionAttributes().put("playerId", playerId);
                messagingTemplate.convertAndSend("/topic/connection/" + sessionId, gameEngine.connect(sessionId));
                break;
            case START:
                log.info("Start game ...");
                log.info("Game started, playerId: {}", playerId);
                // Send quiz questions
                messagingTemplate.convertAndSend("/topic/start/" + playerId, gameEngine.start(sessionId, playerId));
                break;
            case UPDATE:
                log.info("Update game ...");
                int score = reqNode.get("score").asInt();
                gameEngine.update(sessionId, playerId, score);
                break;
            default:
                log.info("Invalid message type");
                break;
        }
    }

    @PostMapping("/api/start")
    public void setUpSession() {
        EventSessionInfo eventSessionInfo = kafkaConsumerService.getNewestMessage();

        // This is information received from Broker of Event services
        String gameId = eventSessionInfo.getGameId();
        String eventId = eventSessionInfo.getEventId();
        String startDate = eventSessionInfo.getStartDate();
        String endDate = eventSessionInfo.getEndDate();
        String startTime = eventSessionInfo.getStartTime();
        String endTime = "23:00:00";

        log.info("{} {} {} {} {} {}", gameId, eventId, startDate, endDate, startTime, endTime);

        Runnable setUpGame = () -> {
            // Save data to MongoDB and get sessionId
            String sessionId = "669fedc17ada690bd952c608";
//            gameEngine.setUp(sessionId);
            log.info("set up session");

            Runnable updateTime = () -> {
                updateTimeAndLeaderboard(sessionId, 1, 2);
            };
            schedulerService.createCronJobs(updateTime, startDate, endDate, startTime, endTime, true);
        };

        schedulerService.createCronJobs(setUpGame, startDate, endDate, startTime, endTime, false);
    }

    private void updateTimeAndLeaderboard(String sessionId, long startTime, int duration) {
        long now = Utils.now();
        log.info("Time: {}", now);
        messagingTemplate.convertAndSend("/topic/time/" + sessionId, now);
        // Update leaderboard when switch question
        // if ((now - startTime) % duration == 0) {
        // sessionsService.getLeaderboardBySessionId(sessionId);
        // messagingTemplate.convertAndSend("/topic/leaderboard/" + sessionId,
        // sessionsService.getLeaderboardBySessionId(sessionId));
        // }
    }
}
