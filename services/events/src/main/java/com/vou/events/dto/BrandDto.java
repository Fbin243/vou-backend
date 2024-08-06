package com.vou.events.dto;

import com.vou.events.common.BrandStatus;

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

    private String brandName;

    private String field;

    private String address;

    private double latitude;

    private double longitude;

    private BrandStatus status;
}