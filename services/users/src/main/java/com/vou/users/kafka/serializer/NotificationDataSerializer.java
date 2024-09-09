package com.vou.users.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.users.model.NotificationData;
import org.apache.kafka.common.serialization.Serializer;

public class NotificationDataSerializer implements Serializer<NotificationData> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationData data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing NotificationData", e);
        }
    }
}
