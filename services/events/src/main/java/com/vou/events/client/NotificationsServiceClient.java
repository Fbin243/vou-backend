package com.vou.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.events.dto.AddUsersRequestDto;

@FeignClient(name = "notifications-service", url = "http://localhost:8086/api/notifications")
public interface NotificationsServiceClient {

    @PostMapping("/users")
    String addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto);
}