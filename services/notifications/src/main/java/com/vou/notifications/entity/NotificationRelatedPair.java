package com.vou.notifications.entity;

import com.vou.pkg.entity.Base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.vou.notifications.common.ActiveStatus;
import com.vou.notifications.model.NotificationRelatedPairId;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications_relatedair")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationRelatedPair extends Base {
    
    @EmbeddedId
    private NotificationRelatedPairId id;

    @Column(name = "active_status", nullable = false)
    private ActiveStatus activeStatus;
}
