package com.vou.events.repository;

import com.vou.events.entity.EventGame;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class EventGameRepositoryImpl implements EventGameRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EventGame findByEventAndGame(String eventId, Long gameId) {
        TypedQuery<EventGame> query = entityManager.createQuery(
                "SELECT eg FROM EventGame eg WHERE eg.event.id = :eventId AND eg.game.id = :gameId",
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
}
