package com.vou.sessions.config;

import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.engine.quizgame.QuizGameEngine;
import com.vou.sessions.engine.shakinggame.ShakingGameEngine;
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
	private final QuizGameEngine quizGameEngine;
	private final ShakingGameEngine shakingGameEngine;
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
		String gameType = headerAccessor.getSessionAttributes().get("gameType").toString();
		log.info("Want to remove sessionId, playerId: {} {}", sessionId, playerId);
		GameEngine gameEngine;
		switch (gameType) {
			case "shaking":
				gameEngine = shakingGameEngine;
				break;
			case "quiz":
				gameEngine = quizGameEngine;
				break;
			default:
				throw new RuntimeException("Invalid game type");
		}
		simpMessagingTemplate.convertAndSend("/topic/connection/" + sessionId, gameEngine.disconnect(sessionId, playerId));
	}
}
