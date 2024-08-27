package com.vou.notifications.entity;

import com.vou.pkg.entity.Base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.model.NotificationUserId;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationUser extends Base {

    @EmbeddedId
    private NotificationUserId id;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "active_status", nullable = false)
    private ActiveStatus activeStatus;
}
