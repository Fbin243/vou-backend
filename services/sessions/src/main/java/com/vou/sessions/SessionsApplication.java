package com.vou.sessions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SessionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionsApplication.class, args);
	}

}
