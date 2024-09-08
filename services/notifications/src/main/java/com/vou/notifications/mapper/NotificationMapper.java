package com.vou.notifications.mapper;

import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.entity.NotificationEntity;

import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {
    
    // convert NotificationEntity to NotificationDto
    public static NotificationDto toDto(NotificationEntity notificationEntity) {
        if (notificationEntity == null) {
            return null;
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notificationEntity.getId());
        notificationDto.setTitle(notificationEntity.getTitle());
        notificationDto.setDescription(notificationEntity.getDescription());
        notificationDto.setImageUrl(notificationEntity.getImageUrl());
        return notificationDto;
    }

    // convert NotificationDto to NotificationEntity
    public static NotificationEntity toEntity(NotificationDto notificationDto) {
        if (notificationDto == null) {
            return null;
        }

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(notificationDto.getId());
        notificationEntity.setTitle(notificationDto.getTitle());
        notificationEntity.setDescription(notificationDto.getDescription());
        notificationEntity.setImageUrl(notificationDto.getImageUrl());
        return notificationEntity;
    }
}