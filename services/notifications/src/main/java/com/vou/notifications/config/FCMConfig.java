// package com.vou.notifications.config;

// import com.google.api.client.util.Value;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.cloud.firestore.Firestore;
// import com.google.cloud.firestore.FirestoreOptions;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;

// import javax.annotation.PostConstruct;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;


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

//             InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("services/notifications/src/main/resources/vou-notifications-system-048e86e9e460.json");
            
//             FirebaseOptions options = new FirebaseOptions.Builder()
//                     .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                     .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app")
//                     .build();

//             // Check if FirebaseApp is already initialized
//             if (FirebaseApp.getApps().isEmpty()) {
//                 FirebaseApp.initializeApp(options);
//             } else {
//                 System.out.println("FirebaseApp already initialized.");
//             }

//             // GoogleCredentials credentials = GoogleCredentials.fromStream("serviceAccountKey.json");
//             // FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
//             //     .setCredentials(credentials)
//             //     .build();
//             // Firestore db = firestoreOptions.getService();

//         } catch (IOException e) {
//             System.err.println("Error initializing Firebase: " + e.getMessage());
//             e.printStackTrace();
//             throw new RuntimeException("Failed to initialize Firebase", e);
//         }
//     }
// }


// // @Configuration
// // public class FCMConfig {
// //     @Value("${firebase-configuration-file}")
// //     private String firebaseConfigPath;

// //     @PostConstruct
// //     public void initialize() {
// //         // log.info("Start init");
// //         try {
// //             FirebaseOptions options = new FirebaseOptions.Builder()
// //                     .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream())).build();
// //             if (FirebaseApp.getApps().isEmpty()) {
// //                 FirebaseApp.initializeApp(options);
// //                 // log.info("Firebase application has been initialized");
// //             }
// //         } catch (IOException e) {
// //             // log.error(e.getMessage());
// //             e.getStackTrace();
// //         }
// //     }
// // }


package com.vou.notifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class FCMConfig {

    private static final Logger logger = Logger.getLogger(FCMConfig.class.getName());

    @PostConstruct
    public void initialize() {
        try {
            // logger.info("Initializing Firebase...");

            // InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("vou-notifications-system-048e86e9e460.json");

            // if (serviceAccount == null) {
            //     logger.info("Service account key file not found in classpath");
            //     throw new RuntimeException("Service account key file not found in classpath");
            // }

            // FirebaseOptions options = new FirebaseOptions.Builder()
            //         .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            //         .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app")
            //         .build();
            logger.info("GOOGLE_APPLICATION_CREDENTIALS: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            FileInputStream serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            // D:/IT/SpringBoot/VOU/vou-backend/services/notifications/src/main/resources/vou-notifications-system-048e86e9e460.json
            logger.info("serviceAccount: " + serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://vou-notifications-system-default-rtdb.asia-southeast1.firebasedatabase.app")
            .build();

            // FirebaseApp.initializeApp(options);


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


// package com.vou.notifications.config;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;

// import javax.annotation.PostConstruct;

// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// @Configuration
// public class FCMConfig {

//     // private static final Logger logger = Logger.getLogger(FCMConfig.class.getName());

//     //     @Bean
//     // public FirebaseApp firebaseApp() throws IOException {
//     //     // Replace this with the actual path to your service account JSON file
//     //     String serviceAccountPath = "C:/path/to/your/serviceAccountKey.json";

//     //     // Load the credentials from the specified file
//     //     GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(serviceAccountPath));
        
//     //     FirebaseOptions options = FirebaseOptions.builder()
//     //         .setCredentials(credentials)
//     //         .setDatabaseUrl("https://your-database-url.firebaseio.com")
//     //         .build();

//     //     if (FirebaseApp.getApps().isEmpty()) {
//     //         return FirebaseApp.initializeApp(options);
//     //     } else {
//     //         return FirebaseApp.getApps().get(0); // Return existing instance
//     //     }
//     // }
// }