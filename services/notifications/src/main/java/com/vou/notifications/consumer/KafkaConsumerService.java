package com.vou.notifications.consumer;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// @Service
// public class NotificationsConsumer {

//     @KafkaListener(topics = "notifications", groupId = "group_id")
//     public void consume(String message) {
//         System.out.println("Received Messasge: " + message);
//     }
// }

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {

    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "notifications", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        messages.add(record.value());
        acknowledgment.acknowledge();
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void clearMessages() {
        messages.clear();
    }
}

