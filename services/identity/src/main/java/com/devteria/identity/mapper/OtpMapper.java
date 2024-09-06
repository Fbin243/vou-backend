package com.devteria.identity.mapper;

import org.mapstruct.Mapper;

import com.devteria.identity.dto.request.OtpDataRequest;
import com.devteria.identity.entity.Otp;

@Mapper(componentModel = "spring")
public interface OtpMapper {
    Otp toOtp(OtpDataRequest otpDataRequest);
}
