package com.vou.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.users.dto.AddUsersRequestDto;
import com.vou.users.model.NotificationRelatedPairId;

import java.util.List;

@FeignClient(name = "notifications-service", url = "http://notifications:8086/notifications/api/notifications")
public interface NotificationsServiceClient {

    @PostMapping("/users")
    String addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto);

    @PostMapping("/addMoreData")
    String addMoreDataToNotification(@RequestBody List<NotificationRelatedPairId> relatedPairs);
}