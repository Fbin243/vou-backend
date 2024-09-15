package com.vou.statistics.client;

import com.vou.statistics.dto.AddUsersRequestDto;
import com.vou.statistics.model.NotificationData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications-service", url = "http://notifications:8086/notifications/api/notifications")
public interface NotificationsServiceClient {
	
	@PostMapping("/send")
	public String sendNotification(NotificationData notificationData);
	
	@PostMapping("/users")
	public String addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto);
}
