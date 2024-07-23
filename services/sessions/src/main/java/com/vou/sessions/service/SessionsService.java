package com.vou.sessions.service;

import com.mongodb.client.MongoClient;
import com.vou.sessions.dto.quizgame.QuizQuestion;
import com.vou.sessions.dto.quizgame.QuizResponse;
import com.vou.sessions.entity.Session;
import com.vou.sessions.entity.UserRecord;
import com.vou.sessions.repository.SessionsRepository;
import com.vou.sessions.service.client.HQTriviaFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionsService implements ISessionsService {
    private static final Logger log = LoggerFactory.getLogger(SessionsService.class);
    private static final String HASH_KEY = "QUIZ";
    private final String QUIZ_QUESTION_KEY = "QUIZ_QUESTION_KEY";
    private final MongoClient mongo;
    private HQTriviaFeignClient hqTriviaFeignClient;
    private RedisTemplate<Object, Object> redisTemplate;
    private SessionsRepository sessionsRepository;

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
    public void createSession() {
        Session session = new Session();
        session.setEventId("1");
        session.setGameId("2");
        List<UserRecord> userRecords = new ArrayList<>();
        userRecords.add(new UserRecord("1", 0, 0));
        userRecords.add(new UserRecord("1", 0, 0));
        userRecords.add(new UserRecord("1", 0, 0));
        session.setUsers(userRecords);
        sessionsRepository.save(session);
    }
}


