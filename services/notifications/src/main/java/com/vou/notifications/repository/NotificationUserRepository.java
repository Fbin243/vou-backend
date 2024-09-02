package com.vou.notifications.repository;

import com.vou.notifications.entity.NotificationUser;
import com.vou.notifications.model.NotificationUserId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationUserRepository extends JpaRepository<NotificationUser, NotificationUserId>, NotificationUserRepositoryCustom {

    List<NotificationUser> findByUserId(String userId);

    List<NotificationUser> findByNotificationId(String notificationId);

    List<String> findUserIdsByNotificationId(String notificationId);

    NotificationUser findByUserIdAndNotificationId(String userId, String notificationId);
}