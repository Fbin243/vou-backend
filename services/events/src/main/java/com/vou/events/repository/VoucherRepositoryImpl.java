package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.Voucher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class VoucherRepositoryImpl implements VoucherRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Voucher> findByBrand(String brandId) {
        TypedQuery<Voucher> query = entityManager.createQuery(
                "SELECT v FROM Voucher v WHERE v.brand.id = :brandId",
                Voucher.class
        );
        query.setParameter("brandId", brandId);
        return query.getResultList();
    }

    @Override
    public List<Voucher> findByBrands(List<String> brandIds) {
        TypedQuery<Voucher> query = entityManager.createQuery(
                "SELECT v FROM Voucher v WHERE v.brand.id IN :brandIds",
                Voucher.class
        );
        query.setParameter("brandIds", brandIds);
        return query.getResultList();
    }

    @Override
    public List<Voucher> findByIds(List<String> ids) {
        TypedQuery<Voucher> query = entityManager.createQuery(
                "SELECT v FROM Voucher v WHERE v.id IN :ids",
                Voucher.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}