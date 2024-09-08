package com.vou.statistics.dto;

import com.vou.statistics.common.UserRole;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandDto {
    private String id;

    private String fullName;

    private String username;

    private String accountId;
    
    private String email;

    private String phone;

    private UserRole role;

    private String brandName;

    private String field;

    private String address;

    private double latitude;

    private double longitude;

    private boolean status;
}