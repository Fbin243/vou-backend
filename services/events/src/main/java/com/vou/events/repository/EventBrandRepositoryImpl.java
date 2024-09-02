package com.vou.events.repository;

import com.vou.events.entity.EventBrand;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<EventBrand> findByBrand(String brandId) {
        TypedQuery<EventBrand> query = entityManager.createQuery(
                "SELECT eb FROM EventBrand eb WHERE eb.brand.id = :brandId",
                EventBrand.class
        );
        query.setParameter("brandId", brandId);
        return query.getResultList();
    }

    @Override
    public List<EventBrand> findByEvent(String eventId) {
        TypedQuery<EventBrand> query = entityManager.createQuery(
                "SELECT eb FROM EventBrand eb WHERE eb.event.id = :eventId",
                EventBrand.class
        );
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    @Override
    public List<EventBrand> findByBrands(List<String> brandIds) {
        TypedQuery<EventBrand> query = entityManager.createQuery(
                "SELECT eb FROM EventBrand eb WHERE eb.brand.id IN :brandIds",
                EventBrand.class
        );
        query.setParameter("brandIds", brandIds);
        return query.getResultList();
    }
}
