package com.vou.events.client;

import com.vou.events.dto.GameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "games-service", url = "http://games:8082/games")
public interface GamesServiceClient {
	
	@GetMapping("/games/{id}")
	GameDto getGameById(@PathVariable("id") Long id);
}
