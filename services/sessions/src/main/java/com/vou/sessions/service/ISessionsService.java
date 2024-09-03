package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;

import java.time.LocalDate;

public interface ISessionsService {
	public SessionDto findSessionByEventIdAndGameIdAndDate(String eventId, String gameId, LocalDate date);
	
	SessionDto createSession(SessionDto sessionDto);
}
