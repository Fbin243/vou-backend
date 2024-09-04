package com.vou.statistics.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherItemsConversionInfo {
    private String playerId;
    private String voucherId;
    private int numberOfVoucher;
    private List<ItemId_Quantity> items;
}
