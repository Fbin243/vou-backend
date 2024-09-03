package com.vou.notifications;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vou.notifications.config.FirebaseProperties;

@SpringBootApplication(scanBasePackages = "com.vou.notifications")
@EnableJpaAuditing
@EnableConfigurationProperties(FirebaseProperties.class)
@EnableJpaRepositories(basePackages = "com.vou.notifications.repository")
public class NotificationsApplication extends AbstractNotificationsApplication {

	// @Bean
	// FirebaseMessaging firebaseMessaging() throws IOException {
	// 	GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource("vou-notifications-system-048e86e9e460.json").getInputStream());

	// 	FirebaseOptions firebaseOptions = FirebaseOptions.builder()
	// 			.setCredentials(credentials)
	// 			.build();

	// 	FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "vou-notifications-system");

	// 	return FirebaseMessaging.getInstance(app);
	// }

	public static void main(String[] args) {
		SpringApplication.run(NotificationsApplication.class, args);
	}


    // @Bean
    // public WebMvcConfigurer corsConfigurer() {
    //     return new WebMvcConfigurer() {
    //         @Override
    //         public void addCorsMappings(CorsRegistry registry) {
    //             registry.addMapping("/**")
    //                     .allowedOrigins("*")  // Allow all origins
    //                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    //                     .allowedHeaders("*")
    //                     .allowCredentials(false);
    //         }
    //     };
    // }
}