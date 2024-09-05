package com.vou.statistics.kafka.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.statistics.dto.TransactionDto;

import java.util.List;

import org.apache.kafka.common.serialization.Deserializer;

public class TransactionDeserializer implements Deserializer<TransactionDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TransactionDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, TransactionDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing TransactionDto", e);
        }
    }
}