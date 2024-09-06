package com.vou.sessions.engine.shakinggame;

import com.vou.pkg.exception.NotFoundException;
import com.vou.sessions.dto.RecordDto;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.entity.SessionEntity;
import com.vou.sessions.entity.shakinggame.ShakingRecordEntity;
import com.vou.sessions.mapper.RecordMapper;
import com.vou.sessions.repository.SessionsRepository;
import com.vou.sessions.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ShakingGameEngine extends GameEngine {
	private static final String EVENT_ITEMS_KEY = "EVENT_ITEMS";
	private SessionsRepository sessionsRepository;
	private RecordMapper shakingRecordMapper;
	
	@Autowired
	ShakingGameEngine(RedisTemplate<String, Object> redisTemplate, SessionsRepository sessionsRepository,
	                  RecordMapper shakingRecordMapper) {
		this.redisTemplate = redisTemplate;
		this.sessionsRepository = sessionsRepository;
		this.shakingRecordMapper = shakingRecordMapper;
	}
	
	@PostConstruct
	public void init() {
		hashOps = redisTemplate.opsForHash();
	}
	
	@Override
	public void setUp(String sessionId) {
		// Check if session is existed
		if (hashOps.hasKey(sessionId, EVENT_ITEMS_KEY)) {
			log.info("Session has been set up");
			return;
		}
	}
	
	@Override
	public Object start(String sessionId, String playerId) {
		long now = Utils.now();
		ShakingRecord shakingRecord = getShakingRecord(sessionId, playerId).orElseGet(
			() -> {
				ShakingRecord tmpShakingRecord = new ShakingRecord();
				tmpShakingRecord.setTurns(3);
				tmpShakingRecord.setTotalTime(0);
				tmpShakingRecord.setStartPlayTime(now);
				return tmpShakingRecord;
			});
		shakingRecord.setStartPlayTime(now);
		putShakingRecord(sessionId, playerId, shakingRecord);
		objectNode.put("turns", shakingRecord.getTurns());
		return objectNode;
	}
	
	private Optional<ShakingRecord> getShakingRecord(String sessionId, String playerId) {
		try {
			ShakingRecord shakingRecord = objectMapper.convertValue(hashOps.get(sessionId, playerId), ShakingRecord.class);
			return Optional.ofNullable(shakingRecord);
		} catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}
	
	private void putShakingRecord(String sessionId, String playerId, ShakingRecord shakingRecord) {
		try {
			hashOps.put(sessionId, playerId, shakingRecord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void update(String sessionId, String playerId, Object update) {
		Integer newTurns = (Integer) update;
		ShakingRecord shakingRecord = getShakingRecord(sessionId, playerId).orElseThrow(
			() -> new NotFoundException("Player record", "sessionId, playerId",
				String.format("%s, %s", sessionId, playerId)));
		shakingRecord.setTurns(newTurns);
		putShakingRecord(sessionId, playerId, shakingRecord);
	}
	
	@Override
	public List<RecordDto> end(String sessionId) {
		List<ShakingRecordEntity> leaderboard = getLeaderboard(sessionId);
		SessionEntity sessionEntity = sessionsRepository.findSessionById(sessionId).orElseThrow(
			() -> new NotFoundException("Session", "sessionId", sessionId));
		
		sessionEntity.setUsers(new ArrayList<>(leaderboard));
		sessionsRepository.save(sessionEntity);
		return new ArrayList<>(shakingRecordMapper.toShakingRecordDtoList(leaderboard));
	}
	
	private List<ShakingRecordEntity> getLeaderboard(String sessionId) {
		Map<String, Object> playerRecords = hashOps.entries(sessionId);
		playerRecords.remove(CONNECTION_KEY);
		playerRecords.remove(EVENT_ITEMS_KEY);
		log.info("getLeaderboardBySessionId, len: {} {}", playerRecords.size(), playerRecords);
		
		List<ShakingRecordEntity> leaderboard = playerRecords.entrySet().stream()
			.map(entry -> {
				String key = entry.getKey();
				Object value = entry.getValue();
				ShakingRecord shakingRecord = objectMapper.convertValue(value, ShakingRecord.class);
				ShakingRecordEntity shakingRecordEntity = new ShakingRecordEntity();
				shakingRecordEntity.setUserId(key);
				shakingRecordEntity.setTurns(shakingRecord.getTurns());
				shakingRecordEntity.setTotalTime(shakingRecord.getTotalTime());
				return shakingRecordEntity;
			})
			.toList();
		
		log.info("Leaderboard {}", leaderboard);
		return leaderboard;
	}
	
	@Override
	protected void updateTotalTime(String sessionId, String playerId) {
		ShakingRecord shakingRecord = getShakingRecord(sessionId, playerId).orElseThrow(
			() -> new NotFoundException("Player record", "sessionId, playerId",
				String.format("%s, %s", sessionId, playerId)));
		if (shakingRecord == null) {
			log.info("Update totalTime failed");
			return;
		}
		
		if (shakingRecord.getStartPlayTime() == -1) {
			return;
		}
		
		shakingRecord.setTotalTime(shakingRecord.getTotalTime() + Utils.now() - shakingRecord.getStartPlayTime());
		shakingRecord.setStartPlayTime(-1); // Means the player is off
		putShakingRecord(sessionId, playerId, shakingRecord);
	}
}
