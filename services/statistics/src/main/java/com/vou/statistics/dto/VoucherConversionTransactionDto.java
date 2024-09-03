package com.vou.statistics.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonTypeName("voucher_conversion")
public class VoucherConversionTransactionDto extends TransactionDto {
    private String eventId;
    private List<ItemId_Quantity> items;
}
