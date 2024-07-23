package com.vou.sessions.dto;

import lombok.Data;

@Data
public class MessageDto {
    private MessageType type;
    private String payload;
}
