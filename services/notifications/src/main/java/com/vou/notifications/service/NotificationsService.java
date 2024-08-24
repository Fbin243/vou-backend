package com.vou.notifications.service;

import java.util.List;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.dto.NotificationUserDto;
import com.vou.notifications.entity.NotificationEntity;
import com.vou.notifications.entity.NotificationUser;
import com.vou.notifications.mapper.NotificationMapper;
import com.vou.notifications.model.NotificationUserId;
import com.vou.notifications.repository.NotificationRepository;
import com.vou.notifications.repository.NotificationUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationsService implements INotificationsService {
    
    private final NotificationRepository        notificationRepository;
    private final NotificationUserRepository    notificationUserRepository;
    private final Firestore                     firestore;

    @Override
    public String createNotification(NotificationDto notificationInfo) {
        // NotificationEntity notificationEntity = NotificationMapper.toEntity(notificationInfo);
        // notificationEntity.setId(null);
        // NotificationEntity createdNotification = notificationRepository.save(notificationEntity);

        // // Save to Firestore
        // saveNotificationToFirestore(createdNotification);

        // return createdNotification.getId().toString();

        try {
            // Create a reference to the notifications collection
            CollectionReference notifications = firestore.collection("notifications");

            // Generate a new document ID
            DocumentReference newNotificationRef = notifications.document();
            String notificationId = newNotificationRef.getId();

            // Set the fields in Firestore
            newNotificationRef.set(notificationInfo); //  not yet check fail or success

            return notificationId;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String addUsersToNotification(NotificationDto notificationInfo, List<String> userIds) {
        // try {
        //     String notificationId = this.createNotification(notificationInfo);

        //     for (String userId : userIds) {
        //         NotificationUserId notificationUserId = new NotificationUserId(notificationId, userId);
        //         NotificationUser notificationUser = new NotificationUser();

        //         notificationUser.setId(notificationUserId);
        //         notificationUser.setActiveStatus(ActiveStatus.ACTIVE);
        //         notificationUserRepository.save(notificationUser);
        //     }
        // } catch (Exception e) {
        //     return false;
        // }

        // return true;

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

    private void saveUserNotificationToFirestore(String notificationId, String userId) {

        try {
            // Define the Firestore collection for notification users
            CollectionReference notificationUsers = firestore.collection("notifications_users");

            // Create a unique document ID for the user-notification pair
            // String userNotificationId = notificationId + "_" + userId;
            DocumentReference userNotificationRef = notificationUsers.document();

            // Set the document fields in Firestore
            userNotificationRef.set(new NotificationUserDto(notificationId, userId, false, ActiveStatus.ACTIVE)); // not yet check fail or success
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
