package com.vou.sessions.dto;

import com.vou.sessions.dto.quizgame.QuizRecordDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
public class SessionDto {
    private ObjectId id;
    private String eventId;
    private String gameId;
    private List<RecordDto> users;
}
