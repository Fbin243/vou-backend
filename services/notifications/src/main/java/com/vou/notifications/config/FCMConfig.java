package com.vou.notifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FCMConfig {

    // @Value("${FIREBASE_SERVICE_ACCOUNT_KEY}")
    // private String serviceAccountKeyPath;

    // @Value("${FIREBASE_DATABASE_URL}")
    // private String databaseUrl;

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");

            if (serviceAccount == null) {
                throw new RuntimeException("Service account key file not found in classpath");
            }

            FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .build();
    
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
