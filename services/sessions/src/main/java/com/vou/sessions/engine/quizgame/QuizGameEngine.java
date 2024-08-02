package com.vou.sessions.engine.quizgame;

import com.amazonaws.services.polly.model.OutputFormat;
import com.vou.pkg.exception.NotFoundException;
import com.vou.sessions.engine.GameEngine;
import com.vou.sessions.repository.SessionsRepository;
import com.vou.sessions.service.client.HQTriviaFeignClient;
import com.vou.sessions.texttospeech.AmazonPollyService;
import com.vou.sessions.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Component
public class QuizGameEngine extends GameEngine {
    private static final String QUIZ_RESPONSE_KEY = "QUIZ_RESPONSE";
    private HQTriviaFeignClient hqTriviaFeignClient;
    private AmazonPollyService amazonPollyService;
    private SessionsRepository sessionsRepository;

    @Autowired
    QuizGameEngine(RedisTemplate<String, Object> redisTemplate, HQTriviaFeignClient triviaFeignClient, AmazonPollyService amazonPollyService) {
        this.redisTemplate = redisTemplate;
        this.hqTriviaFeignClient = triviaFeignClient;
        this.amazonPollyService = amazonPollyService;
    }

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.opsForHash();
    }


    @Override
    public void setUp(String sessionId) {
        // Check if session is exists
        if (hashOps.hasKey(sessionId, QUIZ_RESPONSE_KEY)) {
            log.info("Session has been set up");
            return;
        }

        // Step 1: Fetch data from OpenTrivia
        QuizResponse quizResponse = hqTriviaFeignClient.getQuestions(3);
        shuffleCorrectAnswer(quizResponse);

        // Step 2: Generate audio files from questions by Amazon Polly and save it to AWS S3
        List<QuizQuestion> quizQuestions = quizResponse.getResults();
        for (int i = 0; i < quizQuestions.size(); i++) {
            String ssmlString = amazonPollyService.convertQuizQuestionToSSML(quizQuestions.get(i), i + 1);
            try (InputStream inputStream = amazonPollyService.synthesize(ssmlString, OutputFormat.Mp3)) {
                String key = String.format("questions/%s/%d.mp3", sessionId, i + 1);
                log.info("S3key: {}", key);
                String url = amazonPollyService.uploadToS3(inputStream, key);
                log.info("S3url: {}", url);
                quizQuestions.get(i).setAudioUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to set up quiz game");
            }
        }

        // Step 3: Save quiz response to Redis and MongoDB
        hashOps.put(sessionId, QUIZ_RESPONSE_KEY, quizResponse);
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
        QuizResponse quizResponse = getQuizResponse(sessionId, 10).orElseThrow(
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
            QuizResponse quizResponse = objectMapper.convertValue(hashOps.get(sessionId, QUIZ_RESPONSE_KEY), QuizResponse.class);
            return Optional.of(quizResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private void shuffleCorrectAnswer(QuizResponse quizResponse) {
        for (QuizQuestion quizQuestion : quizResponse.getResults()) {
            quizQuestion.setCorrectAnswerIndex((int) (Math.random() * 4));
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
        playerRecords.remove(QUIZ_RESPONSE_KEY);
        log.info("getLeaderboardBySessionId, len: {} {}", playerRecords.size(), playerRecords);

        List<QuizRecord> leaderboard = playerRecords.values().stream().map(value -> objectMapper.convertValue(value, QuizRecord.class))
                .sorted(Comparator.comparingLong(QuizRecord::getTotalScore))
                .toList();

        log.info("Leaderboard {}", leaderboard);
        return leaderboard;
    }
}
