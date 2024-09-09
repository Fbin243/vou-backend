package com.vou.sessions.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.vou.sessions.kafka.serializer.EventIdSerializer;
import com.vou.sessions.kafka.serializer.NotificationDataSerializer;
import com.vou.sessions.kafka.serializer.TransactionDataSerializer;
import com.vou.sessions.model.EventId;
import com.vou.sessions.model.NotificationData;
import com.vou.sessions.model.TransactionData;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // @Bean
    // public ProducerFactory<String, String> producerFactory() {
    //     Map<String, Object> configProps = new HashMap<>();
    //     configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    //     configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     return new DefaultKafkaProducerFactory<>(configProps);
    // }

    @Bean
    public ProducerFactory<String, TransactionData> transactionProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionDataSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, NotificationData> notificationProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, NotificationDataSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, EventId> eventIdProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventIdSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // @Bean
    // public KafkaTemplate<String, String> kafkaTemplate() {
    //     return new KafkaTemplate<>(producerFactory());
    // }

    @Bean
    public KafkaTemplate<String, TransactionData> kafkaTemplateTransactionDto() {
        return new KafkaTemplate<>(transactionProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, NotificationData> kafkaTemplateNotificationData() {
        return new KafkaTemplate<>(notificationProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, EventId> kafkaTemplateEventId() {
        return new KafkaTemplate<>(eventIdProducerFactory());
    }
}