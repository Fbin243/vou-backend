package com.vou.sessions.engine.quizgame;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuizResponse implements Serializable {
    private int responseCode;
    private List<QuizQuestion> results;
}
