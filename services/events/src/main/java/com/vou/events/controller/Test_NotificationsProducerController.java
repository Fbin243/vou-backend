package com.vou.events.controller;

import com.vou.events.service.Test_NotificationsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class Test_NotificationsProducerController {

    // private final Test_NotificationsProducerService kafkaProducer;

    // @Autowired
    // public Test_NotificationsProducerController(Test_NotificationsProducerService kafkaProducer) {
    //     this.kafkaProducer = kafkaProducer;
    // }

    // @PostMapping("/publish")
    // public void sendMessageToKafka(@RequestParam("message") String message) {
    //     this.kafkaProducer.sendMessage("notifications", message);
    // }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        kafkaTemplate.send("notifications", message);
        return ResponseEntity.ok("Message sent to Kafka successfully");
    }
}
