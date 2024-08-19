package com.vou.notifications.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.vou.notifications.model.EventSessionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaConsumerService {

    Properties props = new Properties();
    private final List<EventSessionInfo> messages = new ArrayList<>();

    @KafkaListener(topics = "event-session", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, EventSessionInfo> record, Acknowledgment acknowledgment) {
        messages.add(record.value());
        acknowledgment.acknowledge();
    }

    public List<EventSessionInfo> getMessages() {
        return new ArrayList<>(messages);
    }

    public EventSessionInfo getNewestMessage() {
        if (messages.isEmpty()) {
            return null; // Return null if there are no messages
        }
        return messages.get(messages.size() - 1);
    }

    public void clearMessages() {
        messages.clear();
    }
}

