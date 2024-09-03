package com.vou.notifications.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.vou.notifications.consumer.NotificationConsumerService;
import com.vou.notifications.entity.NotificationUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.logging.Logger;

public class NotificationUserRepositoryImpl implements NotificationUserRepositoryCustom {

    private static final Logger logger = Logger.getLogger(NotificationConsumerService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Firestore firestore;

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
        // TypedQuery<String> query = entityManager.createQuery(
        //         "SELECT nu.id.userId FROM NotificationUser nu WHERE nu.id.notificationId = :notificationId ORDER BY nu.created_at DESC",
        //         String.class
        // );
        // query.setParameter("notificationId", notificationId);
        // try {
        //     return query.getResultList();
        // } catch (NoResultException e) {
        //     return null;
        // }
        List<String> userIds = new ArrayList<>();
        try {
            logger.info("Retrieving documents from Firestore with notificationId: " + notificationId);
            
            Query query = firestore.collection("notifications_users")
                    .whereEqualTo("notification_id", notificationId);

            // logger.info("Retrieving documents from Firestore with query: " + query.toString());

            // get all record from the query
            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get(); // This returns an ApiFuture
            QuerySnapshot querySnapshot = querySnapshotFuture.get(); // Retrieve the QuerySnapshot

            // ApiFuture<QuerySnapshot> querySnapshotFuture = query.get(); // This returns an ApiFuture
            // QuerySnapshot querySnapshot = querySnapshotFuture.get(); // Retrieve the QuerySnapshot

            logger.info("Retrieved " + querySnapshot.size() + " documents");
            
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                logger.info("Document data: " + document.getData());
                String userId = document.getString("user_id");
                if (userId != null) {
                    userIds.add(userId);
                }
            }
        } catch (InterruptedException e) {
            // Handle InterruptedException
            Thread.currentThread().interrupt(); // Restore the interrupted status
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Handle ExecutionException
            e.printStackTrace();
        }
        
        return userIds;
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