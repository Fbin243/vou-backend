package com.vou.notifications.repository;

import com.vou.notifications.entity.UserToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class UserTokenRepositoryImpl implements UserTokenRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserToken findByToken(String token) {
        TypedQuery<UserToken> query = entityManager.createQuery(
                "SELECT et FROM UserToken et WHERE et.id.token = :token ORDER BY et.created_at DESC",
                UserToken.class
        );
        query.setParameter("token", token);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserToken findByUserId(String userId) {
        TypedQuery<UserToken> query = entityManager.createQuery(
                "SELECT et FROM UserToken et WHERE et.id.userId = :userId ORDER BY et.created_at DESC",
                UserToken.class
        );
        query.setParameter("userId", userId);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String findTokenByUserId(String userId) {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT et.id.token FROM UserToken et WHERE et.id.userId = :userId ORDER BY et.created_at DESC",
                String.class
        );
        query.setParameter("userId", userId);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserToken findByUserIdAndToken(String userId, String token) {
        TypedQuery<UserToken> query = entityManager.createQuery(
                "SELECT et FROM UserToken et WHERE et.id.userId = :userId AND et.id.token = :token ORDER BY et.created_at DESC",
                UserToken.class
        );
        query.setParameter("userId", userId);
        query.setParameter("token", token);
        query.setMaxResults(1); // Limit results to 1
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }      
}
