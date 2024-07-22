package com.vou.sessions.service;

import com.vou.sessions.dto.quizgame.QuizQuestion;
import com.vou.sessions.dto.quizgame.QuizResponse;
import com.vou.sessions.service.client.HQTriviaFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SessionsService implements ISessionsService {
    private static final Logger log = LoggerFactory.getLogger(SessionsService.class);
    private static final String HASH_KEY = "QUIZ";
    private final String QUIZ_QUESTION_KEY = "QUIZ_QUESTION_KEY";
    private HQTriviaFeignClient hqTriviaFeignClient;
    private RedisTemplate<Object, Object> redisTemplate;

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
}
