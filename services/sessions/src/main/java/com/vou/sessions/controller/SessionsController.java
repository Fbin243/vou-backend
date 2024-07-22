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

    @MessageMapping("/start")
    public void startGame(MessageDto message) {
        log.info("start game ... ");

        if (message.getType() == MessageType.START) {
            // Get the set of question and send back to client
            messagingTemplate.convertAndSend("/topic/start", sessionsService.getQuestions(20));
        }
    }

    @Scheduled(fixedRate = 1000)
    public void updateTime() {
        messagingTemplate.convertAndSend("/topic/time", Instant.now().getEpochSecond());
    }
}
