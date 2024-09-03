package com.vou.sessions.consumer;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.entity.SessionEntity;
import com.vou.sessions.schedule.SchedulerService;
import com.vou.sessions.service.ISessionsService;
import com.vou.sessions.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.vou.sessions.model.EventSessionInfo;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final List<EventSessionInfo> messages = new ArrayList<>();
    private ISessionsService sessionsService;
    private SchedulerService schedulerService;
    private SimpMessagingTemplate messagingTemplate;
    private GameEngine gameEngine;

    @KafkaListener(topics = "event-session", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory"
        , concurrency = "1")
    public void listen(ConsumerRecord<String, EventSessionInfo> record, Acknowledgment acknowledgment) {
        EventSessionInfo eventSessionInfo = record.value();
        messages.add(eventSessionInfo);

        String gameId = eventSessionInfo.getGameId();
        String eventId = eventSessionInfo.getEventId();
        String startDate = eventSessionInfo.getStartDate();
        String endDate = eventSessionInfo.getEndDate();
        String startTime = eventSessionInfo.getStartTime() + ":00";
        String endTime = eventSessionInfo.getEndTime() + ":00";

        log.info("Start date : {}", startDate);
        log.info("End date : {}", endDate);
        log.info("Start time : {}", startTime);
        log.info("End time : {}", endTime);

        Runnable setUpGame = () -> {
            log.info("SET UP GAME");
            // Save data to MongoDB and get sessionId
            // Assume this game is quiz game
            SessionDto sessionDto = new SessionDto();
            sessionDto.setEventId(eventId);
            sessionDto.setGameId(gameId);
            sessionDto.setUsers(new ArrayList<>());
            SessionEntity sessionEntity = sessionsService.createSession(sessionDto);

            log.info("New session entity: {}", sessionEntity);
            String sessionId = "66d5611b50080825d7cda679";
            log.info("set up session");
//            gameEngine.setUp(sessionId);
//            Runnable endGame = () -> {
//                log.info("END GAME, SAVE TO MONGODB AND DELETE REDIS");
//                List<QuizRecordDto> leaderboard = gameEngine.end(sessionId);
//                log.info("Leaderboard: {}", leaderboard);
//                messagingTemplate.convertAndSend("/topic/end/" + sessionId, leaderboard);
//            };

            Runnable updateTime = () -> {
                updateTimeAndLeaderboard(sessionId);
            };
//            schedulerService.createCronJobs(endGame, startDate, endDate, endTime, endTime, false);
            schedulerService.createCronJobs(updateTime, startDate, endDate, startTime, endTime, true);
        };

        schedulerService.createCronJobs(setUpGame, startDate, endDate, startTime, endTime, false);

        acknowledgment.acknowledge();
    }

    private void updateTimeAndLeaderboard(String sessionId) {
        long now = Utils.now();
        log.info("Time: {}", now);
        messagingTemplate.convertAndSend("/topic/time/" + sessionId, now);
    }

    public List<EventSessionInfo> getMessages() {
        return new ArrayList<>(messages);
    }

    public EventSessionInfo getNewestMessage() {
        if (messages.isEmpty()) {
            return null; // Return null if there are no messages
        }
        return messages.get(messages.size() - 1);
    }

    public void clearMessages() {
        messages.clear();
    }
}
