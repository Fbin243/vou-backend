package com.devteria.identity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "speedsms")
@Data
public class SpeedSmsConfig {
    private String apiAccessToken;
}
