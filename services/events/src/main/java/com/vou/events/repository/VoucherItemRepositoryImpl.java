package com.vou.events.repository;

import com.vou.events.entity.VoucherItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class VoucherItemRepositoryImpl implements VoucherItemRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public VoucherItem findByVoucherAndItem(String voucherId, String itemId) {
        TypedQuery<VoucherItem> query = entityManager.createQuery(
                "SELECT vi FROM VoucherItem vi WHERE vi.voucher.id = :voucherId AND vi.item.id = :itemId",
                VoucherItem.class
        );
        query.setParameter("voucherId", voucherId);
        query.setParameter("itemId", itemId);
        return query.getSingleResult();
    }
}