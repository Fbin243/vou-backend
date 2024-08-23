package com.vou.notifications.repository;

import java.util.List;

import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.entity.NotificationRelatedPair;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;

public class NotificationRelatedPairRepositoryImpl implements NotificationRelatedPairRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NotificationRelatedPair> findByNotificationId(String notificationId) {
        TypedQuery<NotificationRelatedPair> query = entityManager.createQuery(
                "SELECT nrp FROM NotificationRelatedPair nrp WHERE nrp.id.notificationId = :notificationId AND nrp.activeStatus = :activeStatus",
                NotificationRelatedPair.class
        );
        query.setParameter("notificationId", notificationId);
        query.setParameter("activeStatus", ActiveStatus.ACTIVE);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
