package com.vou.events.config; 

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic eventSession() {
        return new NewTopic("event-session", 1, (short) 1);
    }

    @Bean
    public NewTopic eventNotification() {
        return new NewTopic("event-notification", 1, (short) 1);
    }
}
