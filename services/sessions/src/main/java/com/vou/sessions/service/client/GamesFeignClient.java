package com.vou.sessions.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("games")
public class GamesFeignClient {

}
