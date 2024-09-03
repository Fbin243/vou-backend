package com.vou.sessions.service;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.entity.SessionEntity;

public interface ISessionsService {

    SessionEntity createSession(SessionDto sessionDto);
}
