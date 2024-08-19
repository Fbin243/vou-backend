package com.vou.events.common;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherId_Quantity_ItemIds_Quantities {
    private String                  voucherId;
    private int                     quantityOfVoucher;
    private List<ItemId_Quantity>   itemIds_quantities;
}