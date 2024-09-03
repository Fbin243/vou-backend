package com.vou.notifications.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.model.NotificationInfo;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public void sendNotification(String token, NotificationInfo notificationEntity, Map<String, String> data) {
        Notification notification = Notification.builder()
            .setTitle(notificationEntity.getTitle())
            .setBody(notificationEntity.getDescription())
            .setImage(notificationEntity.getImageUrl())
            .build();

        Message.Builder messageBuilder = Message.builder()
            .setToken(token)
            .setNotification(notification);
    
        if (data != null) {
            messageBuilder.putAllData(data);
        }

        Message message = messageBuilder.build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}