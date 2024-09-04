package com.vou.statistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonTypeName("voucher_used")
public class VoucherUsedTransactionDto extends TransactionDto {
    private String eventId;
}
