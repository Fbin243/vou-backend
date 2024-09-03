package com.vou.statistics.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.statistics.dto.PlayerDto;

@FeignClient(name = "users-service", url = "http://users:8081/players")
public interface UsersServiceClient {

    // get Players By Ids
    @PostMapping("/public/ids")
    List<PlayerDto> getManyPlayersByManyIds(@RequestBody List<String> ids);
}