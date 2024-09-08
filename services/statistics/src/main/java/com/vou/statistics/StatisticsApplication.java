package com.vou.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
@EnableFeignClients
public class StatisticsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}
	
}
