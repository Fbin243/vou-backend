package com.vou.sessions.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventSessionInfo {
    private String eventId;
    
    private String gameId;

    private String startDate;

    private String endDate;

    private String startTime;
}