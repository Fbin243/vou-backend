package com.vou.sessions.engine.quizgame;

import com.vou.pkg.exception.NotFoundException;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.service.client.HQTriviaFeignClient;
import com.vou.sessions.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Component
public class QuizGameEngine extends GameEngine {
    private static final String QUIZ_QUESTION_KEY = "QUIZ_QUESTION";
    private HQTriviaFeignClient hqTriviaFeignClient;

    @Autowired
    QuizGameEngine(RedisTemplate<String, Object> redisTemplate, HQTriviaFeignClient triviaFeignClient) {
        this.redisTemplate = redisTemplate;
        this.hqTriviaFeignClient = triviaFeignClient;
    }

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public Object start(String sessionId, String playerId) {
        long now = Utils.now();
        QuizRecord quizRecord = getQuizRecord(sessionId, playerId).orElseGet(
                () -> {
                    QuizRecord tmpQuizRecord = new QuizRecord();
                    tmpQuizRecord.setTotalScore(0);
                    tmpQuizRecord.setTotalTime(0);
                    tmpQuizRecord.setStartPlayTime(now);
                    return tmpQuizRecord;
                }
        );
        QuizResponse quizResponse = getQuizResponse(sessionId, 20).orElseThrow(
                () -> new RuntimeException("Failed to get quiz response")
        );
        quizRecord.setStartPlayTime(now);
        putQuizRecord(sessionId, playerId, quizRecord);
        objectNode.put("quizResponse", objectMapper.valueToTree(quizResponse));
        objectNode.put("totalScore", quizRecord.getTotalScore());
        return objectNode;
    }

    @Override
    public void update(String sessionId, String playerId, Object update) {
        Integer newScore = (Integer) update;
        QuizRecord quizRecord = getQuizRecord(sessionId, playerId).orElseThrow(
                () -> new NotFoundException("Player record", "sessionId, playerId", String.format("%s, %s", sessionId, playerId))
        );
        quizRecord.setTotalScore(newScore);
        putQuizRecord(sessionId, playerId, quizRecord);
    }

    @Override
    public void end(String sessionId, String playerId) {

    }

    @Override
    public void updateTotalTime(String sessionId, String playerId) {
        QuizRecord quizRecord = getQuizRecord(sessionId, playerId).orElseThrow(
                () -> new NotFoundException("Player record", "sessionId, playerId", String.format("%s, %s", sessionId, playerId))
        );
        if (quizRecord == null) {
            log.info("Update totalTime failed");
        }

        quizRecord.setTotalTime(quizRecord.getTotalTime() + Utils.now() - quizRecord.getStartPlayTime());
        quizRecord.setStartPlayTime(-1); // Means the player is off
        putQuizRecord(sessionId, playerId, quizRecord);
    }

    private Optional<QuizResponse> getQuizResponse(String sessionId, int amount) {
        try {
            QuizResponse quizResponse = objectMapper.convertValue(hashOps.get(sessionId, QUIZ_QUESTION_KEY), QuizResponse.class);
            if (quizResponse == null) {
                quizResponse = hqTriviaFeignClient.getQuestions(amount);
                hashOps.put(sessionId, QUIZ_QUESTION_KEY, quizResponse);
            }
            return Optional.of(quizResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<QuizRecord> getQuizRecord(String sessionId, String playerId) {
        try {
            QuizRecord quizRecord = objectMapper.convertValue(hashOps.get(sessionId, playerId), QuizRecord.class);
            return Optional.ofNullable(quizRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private void putQuizRecord(String sessionId, String playerId, QuizRecord quizRecord) {
        try {
            hashOps.put(sessionId, playerId, quizRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<QuizRecord> getLeaderboard(String sessionId) {
        Map<String, Object> playerRecords = hashOps.entries(sessionId);
        playerRecords.remove(CONNECTION_KEY);
        playerRecords.remove(QUIZ_QUESTION_KEY);
        log.info("getLeaderboardBySessionId, len: {} {}", playerRecords.size(), playerRecords);

        List<QuizRecord> leaderboard = playerRecords.values().stream().map(value -> objectMapper.convertValue(value, QuizRecord.class))
                .sorted(Comparator.comparingLong(QuizRecord::getTotalScore))
                .toList();

        log.info("Leaderboard {}", leaderboard);
        return leaderboard;
    }
}
