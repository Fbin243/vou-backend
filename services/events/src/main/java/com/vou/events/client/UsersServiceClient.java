package com.vou.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "users-service", url = "http://localhost:8081/api/users")
public interface UsersServiceClient {
    // @GetMapping
    // List<UserDto> getAllUsers();
}
