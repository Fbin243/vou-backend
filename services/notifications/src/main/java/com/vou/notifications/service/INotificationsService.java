package com.vou.notifications.service;

import java.util.List;

import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.model.NotificationRelatedPairId;
import com.vou.notifications.model.NotificationRelatedPairId2;

public interface INotificationsService {

    /**
     * Creates a new notification.
     *
     * @param notificationInfo
     * @return the id of the created notification
     */
    default String createNotification(NotificationDto notificationInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds many users to a notification.
     *
     * @param notificationInfo
     * @param userIds
     * @return true if the users was added successfully, false otherwise
     */
    default String addUsersToNotification(NotificationDto notificationInfo, List<String> userIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds more information to a notification.
     *
     * @param notificationId
     * @param relatedKey
     * @param relatedId
     * @return the id of the created notification
     */
    default String addMoreInfo(List<NotificationRelatedPairId2> relatedPairs) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}