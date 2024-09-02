// package com.vou.notifications.firebase;

// import java.io.FileInputStream;

// import org.springframework.stereotype.Service;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import jakarta.annotation.PostConstruct;

// @Service
// public class FirebaseInitialization {

//     private static final Logger log = LoggerFactory.getLogger(FirebaseInitialization.class);

//     @PostConstruct
//     public void initialization() {
//         try {
//             FileInputStream serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
//             GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//             FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
//             if (FirebaseApp.getApps().isEmpty()) {
//                 FirebaseApp.initializeApp(options);
//             }

//             log.info("Firebase has been initialized");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
