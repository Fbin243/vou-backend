package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerVoucherDto {
    private String playerId;

    private String voucherId;

    private String brandId;

    private String voucherName;

    private int quantity;
}