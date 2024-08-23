package com.vou.events.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.events.dto.BrandDto;

@FeignClient(name = "users-service", url = "http://localhost:8081/users")
public interface UsersServiceClient {

    @PostMapping("/brands/public/emails")
    List<BrandDto> getBrandsByEmails(@RequestBody List<String> emails);
}