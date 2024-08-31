package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.EventGame;

public interface EventGameRepositoryCustom {
    EventGame findByEventAndGame(String eventId, Long gameId);
    List<EventGame> findByEvent(String eventId);
}