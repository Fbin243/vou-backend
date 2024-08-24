package com.vou.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vou.notifications.config.FirebaseProperties;

@SpringBootApplication(scanBasePackages = "com.vou.notifications", exclude = {DataSourceAutoConfiguration.class})
@EnableJpaAuditing
@EnableConfigurationProperties(FirebaseProperties.class)
@EnableJpaRepositories(basePackages = "com.vou.notifications.repository")
public class NotificationsApplication extends AbstractNotificationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsApplication.class, args);
	}

}