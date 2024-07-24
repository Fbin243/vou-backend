package com.vou.sessions.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.pkg.exception.NotFoundException;
import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.dto.quizgame.PlayerStats;
import com.vou.sessions.dto.quizgame.QuizQuestion;
import com.vou.sessions.dto.quizgame.QuizResponse;
import com.vou.sessions.entity.Session;
import com.vou.sessions.mapper.SessionMapper;
import com.vou.sessions.repository.SessionsRepository;
import com.vou.sessions.service.client.HQTriviaFeignClient;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionsService implements ISessionsService {
    private static final Logger log = LoggerFactory.getLogger(SessionsService.class);
    private static final String HASH_KEY = "QUIZ";
    private static final String KEY_CONNECTION = "CONNECTION";
    private static final String QUIZ_QUESTION_KEY = "QUIZ_QUESTION_KEY";
    private static ObjectMapper objectMapper = new ObjectMapper();
    @NonNull
    private HQTriviaFeignClient hqTriviaFeignClient;
    @NonNull
    private RedisTemplate<String, Object> redisTemplate;
    @NonNull
    private SessionsRepository sessionsRepository;
    @NonNull
    private SessionMapper sessionMapper;
    @NonNull
    private StringRedisTemplate stringRedisTemplate;
    private HashOperations<String, String, PlayerStats> hashOps;

    @PostConstruct
    public void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public QuizResponse getQuestions(int amount) {
        // Check cache
        QuizResponse quizResponse = (QuizResponse) redisTemplate.opsForHash().get(HASH_KEY, QUIZ_QUESTION_KEY);

        if (quizResponse == null) {
            quizResponse = hqTriviaFeignClient.getQuestions(amount);
            redisTemplate.opsForHash().put(HASH_KEY, QUIZ_QUESTION_KEY, quizResponse);
        }

        List<QuizQuestion> quizQuestions = quizResponse.getResults();
        log.info("getQuestions, len: {} {}", quizQuestions.size(), quizQuestions);
        return quizResponse;
    }

    @Override
    public void saveSessionDataFromRedis() {
        // TODO: Save after game finish
    }

    @Override
    public SessionDto findSessionByEventIdAndGameId(String eventId, String gameId) {
        Session session = sessionsRepository.findSessionByEventIdAndGameId(eventId, gameId).orElseThrow(
                () -> new NotFoundException("Session", "eventId, gameId", String.format("%s, %s", eventId, gameId))
        );
        return sessionMapper.toSessionDto(session);
    }

    @Override
    public String startGame(String payload) {
        // Check user id is new or not
        String playerId, eventId, gameId;
        try {
            log.info("payload: {}", payload);
            JsonNode rootNode = objectMapper.readTree(payload);
            playerId = rootNode.get("playerId").asText();
            eventId = rootNode.get("eventId").asText();
            gameId = rootNode.get("gameId").asText();

            String sessionId = eventId + gameId;
            PlayerStats playerStats = hashOps.get(sessionId, playerId);
            if (playerStats == null) {
                playerStats = new PlayerStats(0, 0);
                hashOps.put(sessionId, playerId, playerStats);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot parse payload");
        }

        return playerId;
    }

    @Override
    public int getNumberOfConnection() {
        String value = stringRedisTemplate.opsForValue().get(KEY_CONNECTION);
        if (value == null) {
            throw new NotFoundException("Redis", "Key", String.format("%s", KEY_CONNECTION));
        }
        return Integer.parseInt(value);
    }
}


