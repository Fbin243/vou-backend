package com.vou.notifications.repository;

import com.vou.notifications.entity.NotificationRelatedPair;

import java.util.List;

public interface NotificationRelatedPairRepositoryCustom {
    public List<NotificationRelatedPair> findByNotificationId(String notificationId);
}
