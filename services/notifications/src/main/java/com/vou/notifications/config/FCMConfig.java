package com.vou.notifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class FCMConfig {

    private static final Logger logger = Logger.getLogger(FCMConfig.class.getName());

    @PostConstruct
    public void initialize() {
        try {
            logger.info("GOOGLE_APPLICATION_CREDENTIALS: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            FileInputStream serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            
            logger.info("serviceAccount: " + serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            // Check if FirebaseApp is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp initialized successfully.");
            } else {
                logger.info("FirebaseApp already initialized.");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error initializing Firebase: " + e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}