package com.vou.statistics.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherConversionTransactionDto extends TransactionDto {
    private String eventId;
    private List<ItemId_Quantity> items;
}
