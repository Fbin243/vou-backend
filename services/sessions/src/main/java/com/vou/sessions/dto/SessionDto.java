package com.vou.sessions.dto;

import com.vou.sessions.engine.quizgame.QuizRecordDto;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class SessionDto {
    private ObjectId id;
    private String eventId;
    private String gameId;
    private List<QuizRecordDto> users;
}
