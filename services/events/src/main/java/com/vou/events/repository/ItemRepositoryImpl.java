package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class ItemRepositoryImpl implements ItemRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Item> findByBrand(String brandId) {
        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item i WHERE i.brand.id = :brandId",
                Item.class
        );
        query.setParameter("brandId", brandId);
        return query.getResultList();
    }

    @Override
    public List<Item> findByBrands(List<String> brandIds) {
        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item i WHERE i.brand.id IN :brandIds",
                Item.class
        );
        query.setParameter("brandIds", brandIds);
        return query.getResultList();
    }
}
