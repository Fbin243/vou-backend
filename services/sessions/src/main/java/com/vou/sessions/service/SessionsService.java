package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.mapper.SessionMapper;
import com.vou.sessions.repository.SessionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionsService implements ISessionsService {
    private SessionsRepository sessionsRepository;
    private SessionMapper sessionMapper;

    @Override
    public SessionDto findSessionByEventIdAndGameId(String eventId, String gameId) {
        return null;
    }
}


