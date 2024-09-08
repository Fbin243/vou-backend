package com.vou.sessions.consumer;

import com.vou.sessions.dto.RecordDto;
import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.engine.quizgame.QuizGameEngine;
import com.vou.sessions.engine.shakinggame.ShakingGameEngine;
import com.vou.sessions.model.EventId;
import com.vou.sessions.model.EventSessionInfo;
import com.vou.sessions.schedule.SchedulerService;
import com.vou.sessions.service.ISessionsService;
import com.vou.sessions.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
	private QuizGameEngine quizGameEngine;
	private ShakingGameEngine shakingGameEngine;
	private KafkaTemplate<String, EventId> kafkaTemplateEventId;
	
	
	@KafkaListener(topics = "event-session", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory"
		, concurrency = "1")
	public void listen(ConsumerRecord<String, EventSessionInfo> record, Acknowledgment acknowledgment) {
		EventSessionInfo eventSessionInfo = record.value();
		messages.add(eventSessionInfo);
		
		String gameId = eventSessionInfo.getGameId();
		String eventId = eventSessionInfo.getEventId();
		String brandId = eventSessionInfo.getBrandId();
		String startDate = eventSessionInfo.getStartDate();
		String endDate = eventSessionInfo.getEndDate();
		String startTime = eventSessionInfo.getStartTime() + ":00";
		String endTime = eventSessionInfo.getEndTime() + ":00";
		String gameType = eventSessionInfo.getGameType();
		
		log.info("Event id : {}", eventId);
		log.info("Brand id : {}", brandId);
		log.info("Game id : {}", gameId);
		log.info("Game type : {}", gameType);
		log.info("Start date : {}", startDate);
		log.info("End date : {}", endDate);
		log.info("Start time : {}", startTime);
		log.info("End time : {}", endTime);
		
		Runnable sendUpComingEventNotification = () -> {
			log.info("SEND UPCOMING NOTIFICATION FOR EVENT_ID: {}", eventId);
			// NotificationInfo notificationInfo =
			// 	new NotificationInfo("You've been invited to " + eventId + " event!", brandId + " " + "invited you to join!", "fa-check");
			// NotificationData notificationData = new NotificationData(notificationInfo, new ArrayList<>());
			
			kafkaTemplateEventId.send("upcoming-event", new EventId(eventId));
		};
		
		Runnable setUpGame = () -> {
			log.info("CRON SET UP GAME");
			// Save data to MongoDB and get sessionId
			// Assume this game is quiz game
			SessionDto sessionDto = new SessionDto();
			sessionDto.setEventId(eventId);
			sessionDto.setGameId(gameId);
			sessionDto.setBrandId(brandId);
			sessionDto.setUsers(new ArrayList<>());
			sessionDto.setDate(ZonedDateTime.now().toLocalDate());
			SessionDto createdSessionDto = sessionsService.createSession(sessionDto);
			String newEndTime = endTime.substring(0, endTime.length() - 3) + ":30";
			
			log.info("New session entity: {}", createdSessionDto);
			String sessionId;
			GameEngine gameEngine;
			switch (gameType) {
				case "quiz":
					gameEngine = quizGameEngine;
					sessionId = createdSessionDto.getId().toHexString();
					break;
				case "shaking":
					gameEngine = shakingGameEngine;
					sessionId = createdSessionDto.getId().toHexString();
					break;
				default:
					throw new RuntimeException("Failed to set up game session");
			}
			
			gameEngine.setUp(sessionId);
			Runnable getLeaderBoard = () -> {
				log.info("GET LEADERBOARD FOR SESSION_ID: {} ", sessionId);
				List<RecordDto> leaderboard = gameEngine.getLeaderBoardForGivingItem(sessionId);
				log.info("Leaderboard: {}", leaderboard);
				messagingTemplate.convertAndSend("/topic/end/" + sessionId, leaderboard);
			};
			
			Runnable endAndReset = () -> {
				log.info("END GAME FOR SESSION_ID: {}", sessionId);
				gameEngine.end(sessionId);
			};
			
			Runnable updateTime = () -> {
				updateTimeAndLeaderboard(sessionId);
			};
			schedulerService.createCronJobs(updateTime, startDate, endDate, startTime, newEndTime, true);
			
			schedulerService.createCronJobs(getLeaderBoard, startDate, endDate, endTime, endTime, false);
			
			// Reset session and save to mongodb after 10 seconds
//			log.info("Clear up redis and save to mongodb after 10 seconds: {}", newEndTime);
			schedulerService.createCronJobs(endAndReset, startDate, endDate, newEndTime, newEndTime, false);
		};
		
		// Set up game before 5 minutes
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(startTime, timeFormatter);
		LocalTime newStartTime = time.minusMinutes(5);
		log.info("SET UP GAME BEFORE 5 MINUTES: {}", newStartTime.toString());
		
		schedulerService.createCronJobs(setUpGame, startDate, endDate, newStartTime.toString() + ":00", endTime, false);
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate _startDate = LocalDate.parse(startDate, dateTimeFormatter);
		LocalDate newStartDate = _startDate.minusDays(1);
		log.info("_Start date: {}", _startDate);
		
		schedulerService.createCronJobs(sendUpComingEventNotification, newStartDate.toString(), newStartDate.toString(), startTime, endTime, false);
		
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
