package com.vou.events.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class EventSessionInfo {
    private String eventId;
    
    private String gameId;

    private String startDate;

    private String endDate;

    private String startTime;
}