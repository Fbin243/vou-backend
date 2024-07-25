package com.vou.sessions.engine.quizgame;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuizQuestion implements Serializable {
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;
}
