package com.vou.sessions.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.vou.sessions.model.EventSessionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    Properties props = new Properties();
    private final List<EventSessionInfo> messages = new ArrayList<>();

    @KafkaListener(topics = "event-session", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory", concurrency = "1")
    public void listen(ConsumerRecord<String, EventSessionInfo> record, Acknowledgment acknowledgment) {
        EventSessionInfo eventSessionInfo = record.value();
        messages.add(eventSessionInfo);
        log.info("Received event session : {}", eventSessionInfo);

        String gameId = eventSessionInfo.getGameId();
        String eventId = eventSessionInfo.getEventId();
        String startDate = eventSessionInfo.getStartDate();
        String endDate = eventSessionInfo.getEndDate();
        String startTime = eventSessionInfo.getStartTime();
        String endTime = eventSessionInfo.getEndTime();

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

