package com.vou.notifications.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.entity.NotificationRelatedPair;
import com.vou.notifications.model.NotificationRelatedPairId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;

public class NotificationRelatedPairRepositoryImpl implements NotificationRelatedPairRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Firestore firestore;

    @Override
    public List<NotificationRelatedPair> findByNotificationId(String notificationId) {
        // TypedQuery<NotificationRelatedPair> query = entityManager.createQuery(
        //         "SELECT nrp FROM NotificationRelatedPair nrp WHERE nrp.id.notificationId = :notificationId AND nrp.activeStatus = :activeStatus",
        //         NotificationRelatedPair.class
        // );
        // query.setParameter("notificationId", notificationId);
        // query.setParameter("activeStatus", ActiveStatus.ACTIVE);
        // try {
        //     return query.getResultList();
        // } catch (NoResultException e) {
        //     return null;
        // }

        List<NotificationRelatedPair> notificationRelatedPairs = new ArrayList<>();
        try {
            Query query = firestore.collection("notifications_relatedPair")
                    .whereEqualTo("notification_id", notificationId)
                    .whereEqualTo("active_status", ActiveStatus.ACTIVE);

            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get(); // This returns an ApiFuture
            QuerySnapshot querySnapshot = querySnapshotFuture.get(); // Retrieve the QuerySnapshot
            
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String relatedKey = document.getString("related_key");
                String relatedId = document.getString("related_id");
                NotificationRelatedPair notificationRelatedPair = new NotificationRelatedPair();
                notificationRelatedPair.setId(new NotificationRelatedPairId());
                notificationRelatedPair.getId().setNotificationId(notificationId);
                notificationRelatedPair.getId().setRelatedKey(relatedKey);
                notificationRelatedPair.getId().setRelatedId(relatedId);
                notificationRelatedPair.setActiveStatus(ActiveStatus.ACTIVE);
                notificationRelatedPairs.add(notificationRelatedPair);
            }
        } catch (InterruptedException e) {
            // Handle InterruptedException
            Thread.currentThread().interrupt(); // Restore the interrupted status
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Handle ExecutionException
            e.printStackTrace();
        }
        
        return notificationRelatedPairs;
    }
    
}