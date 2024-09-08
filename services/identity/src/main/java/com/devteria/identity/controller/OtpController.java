package com.devteria.identity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devteria.identity.dto.request.OtpDataRequest;
import com.devteria.identity.dto.response.OtpDataResponse;
import com.devteria.identity.service.OtpService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OtpController {
    OtpService otpService;

    @PostMapping
    public ResponseEntity<OtpDataResponse> createOtp(@RequestBody OtpDataRequest otpDataRequest) {
        OtpDataResponse response = otpService.createOtp(otpDataRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<OtpDataResponse> getOtpByUsername(@PathVariable String username) {
        OtpDataResponse response = otpService.getOtpByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{username}")
    public ResponseEntity<OtpDataResponse> updateOtp(
            @PathVariable String username, @RequestBody OtpDataRequest otpDataRequest) {
        OtpDataResponse response = otpService.updateOtp(username, otpDataRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<OtpDataResponse> deleteOtpByUsername(@PathVariable String username) {
        OtpDataResponse response = otpService.deleteOtpByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
