package com.vou.notifications.controller;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.web.bind.annotation.*;

// import java.util.ArrayList;
// import java.util.List;

// @RestController
// @RequestMapping("/api/kafka")
// public class ConsumerController {

//     private final List<String> messages = new ArrayList<>();

//     @KafkaListener(topics = "notifications", groupId = "group_id")
//     public void listen(String message) {
//         // print message to console
//         System.out.println("Received Messasge in group - group_id: " + message);
//         messages.add(message);
//     }

//     @GetMapping("/messages")
//     public List<String> getMessages() {
//         return messages;
//     }
// }

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vou.notifications.consumer.KafkaConsumerService;

import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaMessageController {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping("/messages")
    public List<String> getMessages() {
        return kafkaConsumerService.getMessages();
    }

    @GetMapping("/messages/clear")
    public String clearMessages() {
        kafkaConsumerService.clearMessages();
        return "Messages cleared";
    }
}

