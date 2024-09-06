package com.vou.sessions.kafka.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vou.sessions.model.TransactionData;
import org.apache.kafka.common.serialization.Serializer;

public class TransactionDataSerializer implements Serializer<TransactionData> {
	
	private final ObjectMapper objectMapper;
	
	public TransactionDataSerializer() {
		objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);  // Ensure LocalDateTime is handled correctly
	}
	
	@Override
	public byte[] serialize(String topic, TransactionData data) {
		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializing TransactionData", e);
		}
	}
}
