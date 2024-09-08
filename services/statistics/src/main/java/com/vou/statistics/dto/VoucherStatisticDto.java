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
public class VoucherStatisticDto {
    private String eventId;

    private String brandId;

    private String voucherId;

    private int numberOfVoucher;
}
