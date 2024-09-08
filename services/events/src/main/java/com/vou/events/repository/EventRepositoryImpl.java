package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.Event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class EventRepositoryImpl implements EventRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findByIds(List<String> ids) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.id IN :ids",
                Event.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
