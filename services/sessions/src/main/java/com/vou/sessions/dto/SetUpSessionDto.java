package com.vou.sessions.dto;

import lombok.Data;

@Data
public class SetUpSessionDto {
    private String gameId;
    private String eventId;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
