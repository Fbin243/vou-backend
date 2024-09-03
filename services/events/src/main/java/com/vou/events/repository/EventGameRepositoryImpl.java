package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.EventGame;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.vou.events.common.EventIntermediateTableStatus;

public class EventGameRepositoryImpl implements EventGameRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EventGame findByEventAndGame(String eventId, Long gameId) {
        TypedQuery<EventGame> query = entityManager.createQuery(
                "SELECT eg FROM EventGame eg WHERE eg.event.id = :eventId AND eg.game_id = :gameId",
                EventGame.class
        );
        query.setParameter("eventId", eventId);
        query.setParameter("gameId", gameId);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<EventGame> findByEvent(String eventId) {
        TypedQuery<EventGame> query = entityManager.createQuery(
                "SELECT eg FROM EventGame eg WHERE eg.event.id = :eventId AND eg.activeStatus = :activeStatus",
                EventGame.class
        );
        query.setParameter("eventId", eventId);
        query.setParameter("activeStatus", EventIntermediateTableStatus.ACTIVE);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
