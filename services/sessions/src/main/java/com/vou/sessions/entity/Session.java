package com.vou.sessions.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "sessions")
@Data
public class Session {
    @Id
    private ObjectId id;

    @Field(value = "event_id")
    private String eventId;

    @Field(value = "game_id")
    private String gameId;

    @Field(value = "users")
    private List<UserRecord> users;
}