package com.vou.events.repository;

import com.vou.events.entity.EventBrand;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


public class EventBrandRepositoryImpl implements EventBrandRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EventBrand findByEventAndBrand(String eventId, String brandId) {
        TypedQuery<EventBrand> query = entityManager.createQuery(
                "SELECT eb FROM EventBrand eb WHERE eb.event.id = :eventId AND eb.brand.id = :brandId",
                EventBrand.class
        );
        query.setParameter("eventId", eventId);
        query.setParameter("brandId", brandId);
        return query.getSingleResult();
    }
}
