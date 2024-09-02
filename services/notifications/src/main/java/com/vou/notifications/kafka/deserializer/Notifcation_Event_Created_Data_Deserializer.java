package com.vou.notifications.kafka.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vou.notifications.model.Notifcation_Event_Created_Data;

public class Notifcation_Event_Created_Data_Deserializer implements Deserializer<Notifcation_Event_Created_Data> {
    
        private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Notifcation_Event_Created_Data deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Notifcation_Event_Created_Data.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Notifcation_Event_Created_Data", e);
        }
    }
}
