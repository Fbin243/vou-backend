package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.dto.quizgame.QuizResponse;

public interface ISessionsService {
    QuizResponse getQuestions(int amount);

    void saveSessionDataFromRedis();

    SessionDto findSessionByEventIdAndGameId(String eventId, String gameId);

    String startGame(String payload);

    int getNumberOfConnection();
}
