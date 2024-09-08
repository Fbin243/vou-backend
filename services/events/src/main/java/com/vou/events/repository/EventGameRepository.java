package com.vou.events.repository;

import com.vou.events.entity.EventGame;
import com.vou.events.model.EventGameId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventGameRepository extends JpaRepository<EventGame, EventGameId>, EventGameRepositoryCustom {
    EventGame findByEventAndGame(String eventId, Long gameId);
    List<EventGame> findByEvent(String eventId);
}