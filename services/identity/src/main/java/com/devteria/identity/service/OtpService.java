package com.devteria.identity.service;

import org.springframework.stereotype.Service;

import com.devteria.identity.dto.OtpStatus;
import com.devteria.identity.dto.request.OtpDataRequest;
import com.devteria.identity.dto.response.OtpDataResponse;
import com.devteria.identity.entity.Otp;
import com.devteria.identity.mapper.OtpMapper;
import com.devteria.identity.repository.OtpRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpService {
    OtpRepository otpRepository;
    OtpMapper otpMapper;

    public OtpDataResponse createOtp(OtpDataRequest otpDataRequest) {
        Otp otpEntity = otpMapper.toOtp(otpDataRequest);
        otpRepository.save(otpEntity);
        return new OtpDataResponse(OtpStatus.DELIVERED, "OTP created successfully", otpEntity.getOtp());
    }

    public OtpDataResponse getOtpByUsername(String username) {
        Otp otpEntity = otpRepository.findByUsername(username).orElse(null);
        if (otpEntity != null) {
            return new OtpDataResponse(OtpStatus.DELIVERED, "OTP retrieved successfully", otpEntity.getOtp());
        } else {
            return new OtpDataResponse(OtpStatus.FAILED, "OTP not found", null);
        }
    }

    public OtpDataResponse updateOtp(String username, OtpDataRequest otpDataRequest) {
        Otp otpEntity = otpRepository.findByUsername(username).orElse(null);
        if (otpEntity != null) {
            otpEntity.setOtp(otpDataRequest.getOtp());
            otpRepository.save(otpEntity);
            return new OtpDataResponse(OtpStatus.DELIVERED, "OTP updated successfully", otpEntity.getOtp());
        } else {
            return new OtpDataResponse(OtpStatus.FAILED, "OTP not found", null);
        }
    }

    public OtpDataResponse deleteOtpByUsername(String username) {
        Otp otpEntity = otpRepository.findByUsername(username).orElse(null);
        if (otpEntity != null) {
            otpRepository.delete(otpEntity);
            return new OtpDataResponse(OtpStatus.DELIVERED, "OTP deleted successfully", null);
        } else {
            return new OtpDataResponse(OtpStatus.FAILED, "OTP not found", null);
        }
    }
}
