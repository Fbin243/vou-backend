package com.vou.sessions.engine.quizgame;

import com.vou.sessions.engine.Record;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class QuizRecord extends Record implements Serializable {
    private long totalScore;
}