package com.vou.statistics.client;

import com.vou.statistics.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "users-service", url = "http://users:8081/users/players")
public interface UsersServiceClient {
	
	// get Players By Ids
	@PostMapping("/public/ids")
	List<PlayerDto> getManyPlayersByManyIds(@RequestBody List<String> ids);
}
