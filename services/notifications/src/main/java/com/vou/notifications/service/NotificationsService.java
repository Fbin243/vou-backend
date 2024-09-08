package com.vou.notifications.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.vou.notifications.config.FirebaseConfig;
import com.vou.notifications.consumer.NotificationConsumerService;
import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.entity.NotificationRelatedPair;
import com.vou.notifications.model.NotificationInfo;
import com.vou.notifications.repository.NotificationRelatedPairRepository;
import com.vou.notifications.repository.NotificationRepository;
import com.vou.notifications.repository.NotificationUserRepository;
import com.vou.notifications.repository.UserTokenRepository;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationsService implements INotificationsService {
    // private static final Logger                 log = LoggerFactory.getLogger(NotificationsService.class);

    private final FCMService                    fcmService;
    private final NotificationRepository        notificationRepository;
    private final NotificationUserRepository    notificationUserRepository;
    private final NotificationRelatedPairRepository notificationRelatedPairRepository;
    private final UserTokenRepository           userTokenRepository;
    private final FirebaseConfig                firebaseConfig;

    private final Firestore                     firestore;

    private static final Logger logger = Logger.getLogger(NotificationConsumerService.class.getName());

    @Override
    public String createNotification(NotificationDto notificationInfo) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            // ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("notifications").document(notificationInfo.getId()).set(notificationInfo);
            // Save the notification with the id as the document ID
            DocumentReference docRef = dbFirestore.collection("notifications").document(notificationInfo.getId());
            docRef.set(notificationInfo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notificationInfo.getId();
    }

    @Override
    public String addUsersToNotification(NotificationDto notificationInfo, List<String> userIds) {

        String notificationId = null;

        try {
            notificationId = this.createNotification(notificationInfo);

            for (String userId : userIds) {
                saveUserNotificationToFirestore(notificationId, userId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notificationId;
    }

    @Async
    public CompletableFuture<Void> processUserNotificationAsync(String userId, NotificationInfo notificationDto) {
        return CompletableFuture.runAsync(() -> {
            try {
                List<NotificationRelatedPair> relatedPairs = notificationRelatedPairRepository.findByNotificationId(notificationDto.getId());
                String token = null;
                token = userTokenRepository.findTokenByUserId(userId);
    
                if (token != null) {
                    Map<String, String> data = new HashMap<>();
                    for (NotificationRelatedPair relatedPair : relatedPairs) {
                        data.put(relatedPair.getId().getRelatedKey(), relatedPair.getId().getRelatedId());
                    }
        
                    fcmService.sendNotification(token, notificationDto, data);
                    logger.info("Notification sent to userId: " + userId);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error processing notification for userId: " + userId, e);
            }
        });
    }

    private void saveUserNotificationToFirestore(String notificationId, String userId) {

        try {
            // Define the Firestore collection for notification users
            CollectionReference notificationUsers = firestore.collection("notifications_users");

            Map<String, Object> userNotificationData = new HashMap<>();
            userNotificationData.put("user_id", userId);
            userNotificationData.put("notification_id", notificationId);
            userNotificationData.put("is_read ", false);
            userNotificationData.put("active_status", ActiveStatus.ACTIVE);

            // notificationUsers.document(notificationId.substring(0, 18) + userId.substring(0, 18)).set(userNotificationData);

            notificationUsers.document(notificationId).set(userNotificationData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}