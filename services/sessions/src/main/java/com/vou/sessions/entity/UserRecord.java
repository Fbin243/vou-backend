package com.vou.sessions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecord {
    private String userId;
    private int duration;
    private int score;
}
