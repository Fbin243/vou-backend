package com.vou.events.client;

import com.vou.events.dto.BrandDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "users-service", url = "http://users:8081/users")
public interface UsersServiceClient {
	
	@GetMapping("/brands/{brandId}")
	BrandDto getBrand(@PathVariable String brandId);
	
	@PostMapping("/brands/public/emails")
	List<BrandDto> getBrandsByEmails(@RequestBody List<String> emails);
}
