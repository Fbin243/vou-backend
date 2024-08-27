package com.vou.notifications.repository;

import com.vou.notifications.entity.NotificationUser;

import java.util.List;

public interface NotificationUserRepositoryCustom {
    public List<NotificationUser> findByUserId(String userId);
    public List<NotificationUser> findByNotificationId(String notificationId);
    public List<String> findUserIdsByNotificationId(String notificationId);
    public NotificationUser findByUserIdAndNotificationId(String userId, String notificationId);
}
