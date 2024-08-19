package com.vou.events.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.events.model.EventSessionInfo;
import org.apache.kafka.common.serialization.Serializer;

public class EventSessionInfoSerializer implements Serializer<EventSessionInfo> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EventSessionInfo data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing EventSessionInfo", e);
        }
    }
}
