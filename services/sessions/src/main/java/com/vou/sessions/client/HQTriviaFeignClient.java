package com.vou.sessions.client;

import com.vou.sessions.engine.quizgame.QuizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HQTriviaClient", url = "https://opentdb.com")
public interface HQTriviaFeignClient {

    @GetMapping(value = "/api.php?type=multiple", consumes = "application/json")
    QuizResponse getQuestions(@RequestParam("amount") int amount);
}
