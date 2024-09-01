package com.vou.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vou.events.dto.GameDto;

@FeignClient(name = "games-service", url = "http://localhost:8082/games")
public interface GamesServiceClient {

    @GetMapping("/games/{id}")
    GameDto getGameById(@PathVariable("id") Long id);
}
