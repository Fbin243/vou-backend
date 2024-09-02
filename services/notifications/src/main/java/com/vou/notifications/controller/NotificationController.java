package com.vou.notifications.controller;

import com.vou.notifications.dto.AddUsersRequestDto;
import com.vou.notifications.dto.NotificationDto;
import com.vou.notifications.service.NotificationsService;
import com.vou.notifications.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private NotificationsService notificationsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestParam String userId, @RequestParam String token) {
        tokenService.saveOrUpdateToken(userId, token);
        return ResponseEntity.ok("Token saved successfully.");
    }

    @PostMapping("/users")
    public String addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto) {
        String notificationId = null;
        log.info("Adding users to notification: " + addUsersRequestDto.getNotification().getId());
        try {
            notificationId = notificationsService.addUsersToNotification(addUsersRequestDto.getNotification(), addUsersRequestDto.getUserIds());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notificationId;
    }

    @PostMapping("/create")
    public String createNotification(@RequestBody NotificationDto notificationDto) {
        log.info("Creating notification: " + notificationDto.getId());
        String notificationId = null;
        try {
            notificationId = notificationsService.createNotification(notificationDto);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notificationId;
    }
}