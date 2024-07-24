package com.vou.sessions.controller;

import com.vou.sessions.dto.MessageDto;
import com.vou.sessions.dto.MessageType;
import com.vou.sessions.service.ISessionsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
@AllArgsConstructor
public class SessionsController {
    private static final Logger log = LoggerFactory.getLogger(SessionsController.class);
    private SimpMessagingTemplate messagingTemplate;
    private ISessionsService sessionsService;

    @MessageMapping("/game")
    public void executeGame(MessageDto message) {
        if (message.getType() == MessageType.START) {
            log.info("Start game ...");
            String playerId = sessionsService.startGame(message.getPayload());
            log.info("Game started, playerId {}", playerId);
            // Send quiz questions
            messagingTemplate.convertAndSend("/topic/start/" + playerId, sessionsService.getQuestions(20));
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
    public void getNumberOfConnections() {
        log.info("Get number of connections ... ");
        // Send number of active players
        messagingTemplate.convertAndSend("/topic/connection", sessionsService.getNumberOfConnection());
    }

    @Scheduled(fixedRate = 1000)
    public void updateTime() {
        messagingTemplate.convertAndSend("/topic/time", Instant.now().getEpochSecond());
    }
}
