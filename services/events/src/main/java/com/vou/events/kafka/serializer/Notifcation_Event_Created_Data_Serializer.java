package com.vou.events.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.events.model.Notifcation_Event_Created_Data;
import org.apache.kafka.common.serialization.Serializer;

public class Notifcation_Event_Created_Data_Serializer implements Serializer<Notifcation_Event_Created_Data> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Notifcation_Event_Created_Data data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Notifcation_Event_Created_Data", e);
        }
    }
    
}
