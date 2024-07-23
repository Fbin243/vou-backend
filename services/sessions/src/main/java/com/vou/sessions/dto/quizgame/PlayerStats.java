package com.vou.sessions.dto.quizgame;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PlayerStats implements Serializable {
    private int totalTime;
    private int totalScore;
}



