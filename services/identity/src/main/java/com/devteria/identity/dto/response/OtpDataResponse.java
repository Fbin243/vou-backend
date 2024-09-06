package com.devteria.identity.dto.response;

import com.devteria.identity.dto.OtpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDataResponse {
    private OtpStatus status;
    private String message;
    private String otp;
}
