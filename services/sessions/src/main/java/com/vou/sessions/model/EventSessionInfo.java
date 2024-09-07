package com.vou.sessions.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Optional: to exclude null fields from serialization
@JsonSerialize
public class EventSessionInfo {
	private String eventId;
	
	private String gameId;
	
	private String gameType;
	
	private String brandId;
	
	private String startDate;
	
	private String endDate;
	
	private String startTime;
	
	private String endTime;
}
