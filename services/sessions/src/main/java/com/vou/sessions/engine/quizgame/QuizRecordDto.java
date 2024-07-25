package com.vou.sessions.engine.quizgame;

import lombok.Data;

@Data
public class QuizRecordDto {
    private String userId;
    private long totalTime;
    private long totalScore;
}
