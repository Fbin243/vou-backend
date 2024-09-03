package com.vou.sessions.dto.quizgame;

import com.vou.sessions.dto.RecordDto;
import lombok.Data;

@Data
public class QuizRecordDto extends RecordDto {
    private long totalScore;
}
