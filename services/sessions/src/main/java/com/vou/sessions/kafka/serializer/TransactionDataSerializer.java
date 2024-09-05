package com.vou.sessions.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.sessions.model.TransactionData;

import org.apache.kafka.common.serialization.Serializer;

public class TransactionDataSerializer implements Serializer<TransactionData> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, TransactionData data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing TransactionData", e);
        }
    }
}
