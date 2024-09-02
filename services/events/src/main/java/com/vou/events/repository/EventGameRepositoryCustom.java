package com.vou.events.repository;

import com.vou.events.entity.EventGame;

public interface EventGameRepositoryCustom {
    EventGame findByEventAndGame(String eventId, Long gameId);
}