package com.vou.events.client;

import com.vou.events.dto.AddUsersRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications-service", url = "http://notifications:8086/notifications/api/notifications")
public interface NotificationsServiceClient {
	
	@PostMapping("/users")
	String addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto);
}
