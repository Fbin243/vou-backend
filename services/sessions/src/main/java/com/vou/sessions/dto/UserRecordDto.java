package com.vou.sessions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecordDto {
    private String userId;
    private int duration;
    private int score;
}
