package com.vou.notifications.controller;

import com.vou.notifications.dto.AddUsersRequestDto;
import com.vou.notifications.service.NotificationsService;
import com.vou.notifications.service.TokenService;
import com.vou.pkg.dto.ResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

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
    public ResponseEntity<ResponseDto> addUsersToNotification(@RequestBody AddUsersRequestDto addUsersRequestDto) {
        notificationsService.addUsersToNotification(addUsersRequestDto.getNotification(), addUsersRequestDto.getUserIds());
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Users added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
