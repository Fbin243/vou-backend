package com.vou.notifications.config;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


// @Configuration
// public class FCMConfig {

//     // @Value("${FIREBASE_SERVICE_ACCOUNT_KEY}")
//     // private String serviceAccountKeyPath;

//     // @Value("${FIREBASE_DATABASE_URL}")
//     // private String databaseUrl;

//     @PostConstruct
//     public void initialize() {
//         try {
//         //    InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");

//         //    if (serviceAccount == null) {
//         //        throw new RuntimeException("Service account key file not found in classpath");
//         //    }

//         //    FirebaseOptions options = FirebaseOptions.builder()
//         //    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//         //    .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app")
//         //    .build();

//         //    // Check if FirebaseApp is already initialized
//         //    if (FirebaseApp.getApps().isEmpty()) {
//         //        FirebaseApp.initializeApp(options);
//         //    } else {
//         //        System.out.println("FirebaseApp already initialized.");
//         //    }

//             // InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
            
//             // FirebaseOptions options = new FirebaseOptions.Builder()
//             //         .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//             //         .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app")
//             //         .build();

//             // // Check if FirebaseApp is already initialized
//             // if (FirebaseApp.getApps().isEmpty()) {
//             //     FirebaseApp.initializeApp(options);
//             // } else {
//             //     System.out.println("FirebaseApp already initialized.");
//             // }

//             GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("serviceAccountKey.json"));
//             FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
//                 .setCredentials(credentials)
//                 .build();
//             Firestore db = firestoreOptions.getService();

//         } catch (IOException e) {
//             System.err.println("Error initializing Firebase: " + e.getMessage());
//             e.printStackTrace();
//             throw new RuntimeException("Failed to initialize Firebase", e);
//         }
//     }
// }


@Configuration
public class FCMConfig {
    @Value("${firebase-configuration-file}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        // log.info("Start init");
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                // log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            // log.error(e.getMessage());
            e.getStackTrace();
        }
    }
}
