package com.vou.events.repository;

import com.vou.events.entity.EventVoucher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


public class EventVoucherRepositoryImpl implements EventVoucherRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EventVoucher findByEventAndVoucher(String eventId, String voucherId) {
        TypedQuery<EventVoucher> query = entityManager.createQuery(
                "SELECT ev FROM EventVoucher ev WHERE ev.event.id = :eventId AND ev.voucher.id = :voucherId",
                EventVoucher.class
        );
        query.setParameter("eventId", eventId);
        query.setParameter("voucherId", voucherId);
        return query.getSingleResult();
    }
}
