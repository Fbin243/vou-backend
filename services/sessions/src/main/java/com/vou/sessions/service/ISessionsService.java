package com.vou.sessions.service;

import com.vou.sessions.dto.quizgame.QuizResponse;

public interface ISessionsService {
    QuizResponse getQuestions(int amount);

    void createSession();
}
