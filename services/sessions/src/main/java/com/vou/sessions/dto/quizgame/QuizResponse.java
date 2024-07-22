package com.vou.sessions.dto.quizgame;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuizResponse implements Serializable {
    private int response_code;
    private List<QuizQuestion> results;
}
