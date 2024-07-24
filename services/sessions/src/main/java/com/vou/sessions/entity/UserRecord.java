package com.vou.sessions.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class UserRecord {

    @Field(value = "user_id")
    private String userId;

    @Field(value = "duration")
    private int duration;

    @Field(value = "score")
    private int score;
}
