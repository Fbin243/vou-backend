package com.vou.notifications.consumer;

import com.vou.notifications.service.FCMService;

import lombok.AllArgsConstructor;

import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.entity.NotificationEntity;
import com.vou.notifications.entity.NotificationRelatedPair;
import com.vou.notifications.mapper.NotificationMapper;
import com.vou.notifications.repository.NotificationRelatedPairRepository;
import com.vou.notifications.repository.NotificationUserRepository;
import com.vou.notifications.repository.UserTokenRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationConsumerService {

    private final FCMService fcmService;
    private final NotificationUserRepository notificationUserRepository;
    private final UserTokenRepository userTokenRepository;
    private final NotificationRelatedPairRepository notificationRelatedPairRepository;

    @KafkaListener(topics = "event-notification", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listenEventNotification(ConsumerRecord<String, NotificationDto> record, Acknowledgment acknowledgment) {
        try {
            NotificationDto notificationDto = record.value();
            NotificationEntity notificationEntity = NotificationMapper.toEntity(notificationDto);

            List<String> userIds = new ArrayList<>();
            userIds = notificationUserRepository.findUserIdsByNotificationId(notificationDto.getId());
            
            for (String userId : userIds) {
                
                List<NotificationRelatedPair> relatedPairs = notificationRelatedPairRepository.findByNotificationId(notificationDto.getId());
                String token = userTokenRepository.findTokenByUserId(userId);

                Map<String, String> data = new HashMap<>();
                for (NotificationRelatedPair relatedPair : relatedPairs) {
                    data.put(relatedPair.getId().getRelatedKey(), relatedPair.getId().getRelatedId());
                }

                fcmService.sendNotification(token, notificationEntity, data);
            }

            acknowledgment.acknowledge();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
}
