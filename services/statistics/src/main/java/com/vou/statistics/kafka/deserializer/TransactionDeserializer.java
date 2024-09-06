package com.vou.statistics.kafka.deserializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vou.statistics.dto.TransactionDto;

import java.util.List;

import org.apache.kafka.common.serialization.Deserializer;

public class TransactionDeserializer implements Deserializer<TransactionDto> {

    private final ObjectMapper objectMapper;

    public TransactionDeserializer() {
        objectMapper = new ObjectMapper();
        // objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  // Enable polymorphic deserialization
        objectMapper.registerModule(new JavaTimeModule());  // Handle LocalDateTime properly
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
    }

    @Override
    public TransactionDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, TransactionDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing TransactionDto", e);
        }
    }

    @Override
    public void close() {
        // Clean up resources if needed
    }
}