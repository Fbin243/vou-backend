package com.vou.sessions.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionsService implements ISessionsService {
    private static final Logger log = LoggerFactory.getLogger(SessionsService.class);
    private static final String CONNECTION_KEY = "CONNECTION";
    private static final String QUIZ_QUESTION_KEY = "QUIZ_QUESTION";
    @NonNull
    private HQTriviaFeignClient hqTriviaFeignClient;
    @NonNull
    private SessionsRepository sessionsRepository;
    @NonNull
    private SessionMapper sessionMapper;
    @NonNull
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOps;

    @PostConstruct
    public void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public QuizResponse getQuestionsBySessionId(String sessionId, int amount) {
        // Check cache
        QuizResponse quizResponse = (QuizResponse) hashOps.get(sessionId, QUIZ_QUESTION_KEY);

        if (quizResponse == null) {
            quizResponse = hqTriviaFeignClient.getQuestions(amount);
            redisTemplate.opsForHash().put(sessionId, QUIZ_QUESTION_KEY, quizResponse);
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
    public void createPlayerRecord(String sessionId, String playerId) {
        // Check user id is new or not
        PlayerStats playerStats = (PlayerStats) hashOps.get(sessionId, playerId);
        if (playerStats == null) {
            playerStats = new PlayerStats(0, 0);
            hashOps.put(sessionId, playerId, playerStats);
        }
    }

    @Override
    public int getNumberOfConnectionBySessionId(String sessionId) {
        Integer value = (Integer) hashOps.get(sessionId, CONNECTION_KEY);
        if (value == null) {
            hashOps.put(sessionId, CONNECTION_KEY, 1);
        } else {
            value = value + 1;
            hashOps.put(sessionId, CONNECTION_KEY, value);
        }

        return value == null ? 1 : value;
    }
}


