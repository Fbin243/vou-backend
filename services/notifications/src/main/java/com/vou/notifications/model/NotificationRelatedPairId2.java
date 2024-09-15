package com.vou.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import com.vou.notifications.common.ActiveStatus;

import jakarta.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationRelatedPairId2 implements Serializable {
    @Column(name = "notification_id", nullable = false)
    private String notificationId;

    @Column(name = "related_key", nullable = false)
    private String relatedKey;

    @Column(name = "related_id", nullable = false)
    private String relatedId;

    @Column(name = "active_status", nullable = false)
    private ActiveStatus activeStatus;
}