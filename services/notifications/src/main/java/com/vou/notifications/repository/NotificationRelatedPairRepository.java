package com.vou.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vou.notifications.entity.NotificationRelatedPair;
import com.vou.notifications.model.NotificationRelatedPairId;

import java.util.List;

@Repository
public interface NotificationRelatedPairRepository extends JpaRepository<NotificationRelatedPair, NotificationRelatedPairId>, NotificationRelatedPairRepositoryCustom {

    List<NotificationRelatedPair> findByNotificationId(String notificationId);
}
