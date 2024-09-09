package com.vou.sessions.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vou.pkg.exception.NotFoundException;
import com.vou.sessions.dto.RecordDto;
import com.vou.sessions.entity.RecordEntity;
import com.vou.sessions.entity.SessionEntity;
import com.vou.sessions.mapper.RecordMapper;
import com.vou.sessions.repository.SessionsRepository;
import com.vou.sessions.service.SessionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class GameEngine {
	protected static final String CONNECTION_KEY = "CONNECTION";
	protected static final Logger log = LoggerFactory.getLogger(SessionsService.class);
	protected static ObjectMapper objectMapper = new ObjectMapper();
	protected static final ObjectNode objectNode = objectMapper.createObjectNode();
	protected RedisTemplate<String, Object> redisTemplate;
	protected HashOperations<String, String, Object> hashOps;
	protected SessionsRepository sessionsRepository;
	protected RecordMapper recordMapper;
	
	public Object connect(String sessionId) {
		Integer value = (Integer) hashOps.get(sessionId, CONNECTION_KEY);
		if (value == null) {
			hashOps.put(sessionId, CONNECTION_KEY, 1);
		} else {
			value = value + 1;
			hashOps.put(sessionId, CONNECTION_KEY, value);
		}
		
		log.info("--> CHECK Connection: {}", value);
		
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
	
	public List<RecordDto> getLeaderBoardForGivingItem(String sessionId) {
		List<RecordEntity> leaderboard = getLeaderboard(sessionId);
		return new ArrayList<>(recordMapper.mapRecordEntityListToRecordDtoList(leaderboard));
	}
	
	public void end(String sessionId) {
		List<RecordEntity> leaderboard = getLeaderboard(sessionId);
		// Save leaderboard to mongodb
		SessionEntity sessionEntity = sessionsRepository.findSessionById(sessionId).orElseThrow(
			() -> new NotFoundException("Session", "sessionId", sessionId));
		
		sessionEntity.setUsers(new ArrayList<>(leaderboard));
		sessionsRepository.save(sessionEntity);
		log.info("1. SAVE TO MONGODB: ", sessionEntity);
		
		// Reset redis for this session
		redisTemplate.delete(sessionId);
		log.info("2. RESET REDIS FOR SESSION: {}", sessionId);
	}
	
	public abstract void setUp(String sessionId);
	
	public abstract Object start(String sessionId, String playerId);
	
	public abstract void update(String sessionId, String playerId, Object update);
	
	protected abstract void updateTotalTime(String sessionId, String playerId);
	
	protected abstract List<RecordEntity> getLeaderboard(String sessionId);
}
