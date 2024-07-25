package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;

public interface ISessionsService {
    SessionDto findSessionByEventIdAndGameId(String eventId, String gameId);
}
