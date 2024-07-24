package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.dto.quizgame.QuizResponse;

public interface ISessionsService {
    QuizResponse getQuestionsBySessionId(String sessionId, int amount);

    void saveSessionDataFromRedis();

    SessionDto findSessionByEventIdAndGameId(String eventId, String gameId);

    void createPlayerRecord(String sessionId, String playerId);

    int getNumberOfConnectionBySessionId(String sessionId);
}
