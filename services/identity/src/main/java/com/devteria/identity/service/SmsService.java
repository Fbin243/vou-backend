package com.devteria.identity.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.devteria.identity.configuration.SpeedSMSAPI;
import com.devteria.identity.dto.OtpStatus;
import com.devteria.identity.dto.request.OtpDataRequest;
import com.devteria.identity.dto.request.OtpRequest;
import com.devteria.identity.dto.response.OtpDataResponse;
import com.devteria.identity.dto.response.OtpResponseDto;

@Service
public class SmsService {
    private final SpeedSMSAPI speedSMSAPI;
    private final OtpService otpService;

    public SmsService(OtpService otpService) {
        // Initialize SpeedSMSAPI with your access token
        this.speedSMSAPI = new SpeedSMSAPI("A72mXM8yQbOcjYOZ-2FDcbVQ9psP6WKY");
        this.otpService = otpService;
    }

    public OtpResponseDto sendSMS(OtpRequest otpRequest) {
        OtpResponseDto otpResponseDto;
        try {
            // Generate a 6-digit OTP
            String otp = generateOTP();
            String otpMessage = String.format("[VOU] Your verification number is %s.", otp); // Include brand name

            // Send the SMS using SpeedSMS API
            String response =
                    speedSMSAPI.sendSMS(otpRequest.getPhone(), otpMessage, 5, "2df220516fa5771d"); // sms_type = 5

            // Process the response
            otpResponseDto = processResponse(response);

            // Check if username already exists in the database
            OtpDataResponse existingOtp = otpService.getOtpByUsername(otpRequest.getUsername());

            OtpDataRequest otpDataRequest = new OtpDataRequest(otpRequest.getUsername(), otp); // Create OtpDataRequest

            if (existingOtp != null && existingOtp.getOtp() != null) {
                // Update existing OTP
                otpService.updateOtp(otpRequest.getUsername(), otpDataRequest);
            } else {
                // Create new OTP
                otpService.createOtp(otpDataRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    private OtpResponseDto processResponse(String response) {
        // Parse the JSON response and create an OtpResponseDto
        if (response.contains("\"status\":\"success\"")) {
            return new OtpResponseDto(OtpStatus.DELIVERED, "OTP sent successfully.");
        } else {
            return new OtpResponseDto(OtpStatus.FAILED, "Failed to send OTP: " + response);
        }
    }

    private String generateOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Generates a 6-digit OTP
    }
}
