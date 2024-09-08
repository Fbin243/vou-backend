// package com.devteria.identity.initializer;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
//
// import com.devteria.identity.configuration.TwilioConfig;
// import com.twilio.Twilio;
//
// import jakarta.annotation.PostConstruct;
//
// @Configuration
// public class TwilioInitializer {
//    @Autowired
//    private TwilioConfig twilioConfig;
//
//    @PostConstruct
//    public void setup() {
//        // Log the Account SID and Auth Token for debugging
//        System.out.println("Twilio Account SID: " + twilioConfig.getAccountSid());
//        System.out.println("Twilio Auth Token: " + twilioConfig.getAuthToken());
//        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//    }
// }
