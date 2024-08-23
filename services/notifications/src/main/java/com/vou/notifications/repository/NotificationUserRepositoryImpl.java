package com.vou.notifications.repository;

import java.util.List;

import com.vou.notifications.entity.NotificationUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class NotificationUserRepositoryImpl implements NotificationUserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NotificationUser> findByUserId(String userId) {
        TypedQuery<NotificationUser> query = entityManager.createQuery(
                "SELECT nu FROM NotificationUser nu WHERE nu.id.userId = :userId ORDER BY nu.created_at DESC",
                NotificationUser.class
        );
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<NotificationUser> findByNotificationId(String notificationId) {
        TypedQuery<NotificationUser> query = entityManager.createQuery(
                "SELECT nu FROM NotificationUser nu WHERE nu.id.notificationId = :notificationId ORDER BY nu.created_at DESC",
                NotificationUser.class
        );
        query.setParameter("notificationId", notificationId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<String> findUserIdsByNotificationId(String notificationId) {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT nu.id.userId FROM NotificationUser nu WHERE nu.id.notificationId = :notificationId ORDER BY nu.created_at DESC",
                String.class
        );
        query.setParameter("notificationId", notificationId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public NotificationUser findByUserIdAndNotificationId(String userId, String notificationId) {
        TypedQuery<NotificationUser> query = entityManager.createQuery(
                "SELECT nu FROM NotificationUser nu WHERE nu.id.userId = :userId AND nu.id.notificationId = :notificationId ORDER BY nu.created_at DESC",
                NotificationUser.class
        );
        query.setParameter("userId", userId);
        query.setParameter("notificationId", notificationId);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
