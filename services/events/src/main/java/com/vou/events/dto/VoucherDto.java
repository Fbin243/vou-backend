package com.vou.events.dto;

import com.vou.events.common.VoucherStatus;
import com.vou.events.common.VoucherUnitValue;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherDto {
    private String id;
    
    private BrandDto brand;

    private String voucherCode;

    private String qrCode;

    private String image;

    private double value;

    private String description;

    private LocalDateTime expiredDate;

    private VoucherStatus status;

    private VoucherUnitValue unitValue;
}