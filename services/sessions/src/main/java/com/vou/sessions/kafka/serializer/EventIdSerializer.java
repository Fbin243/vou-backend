package com.vou.sessions.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.sessions.model.EventId;

import org.apache.kafka.common.serialization.Serializer;

public class EventIdSerializer implements Serializer<EventId> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EventId data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing EventId", e);
        }
    }
}
