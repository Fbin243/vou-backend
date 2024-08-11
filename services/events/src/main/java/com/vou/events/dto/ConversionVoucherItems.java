package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import com.vou.events.common.ItemId_Quantity;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConversionVoucherItems {
    private String voucherId;
    private List<ItemId_Quantity> itemIds_quantities;
}