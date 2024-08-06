package com.vou.sessions.engine.quizgame;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
public class QuizRecordEntity {

    @Field(value = "user_id")
    private String userId;

    @Field(value = "total_time")
    private long totalTime;

    @Field(value = "total_score")
    private long totalScore;

    @Field(value = "start_date")
    private LocalDate startDate;
}
