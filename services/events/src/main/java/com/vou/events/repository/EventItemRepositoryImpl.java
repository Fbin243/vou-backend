package com.vou.events.repository;

import com.vou.events.entity.EventItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


public class EventItemRepositoryImpl implements EventItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EventItem findByEventAndItem(String eventId, String itemId) {
        TypedQuery<EventItem> query = entityManager.createQuery(
                "SELECT ei FROM EventItem ei WHERE ei.event.id = :eventId AND ei.item.id = :itemId",
                EventItem.class
        );
        query.setParameter("eventId", eventId);
        query.setParameter("itemId", itemId);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
