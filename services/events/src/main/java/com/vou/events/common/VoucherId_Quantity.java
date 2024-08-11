package com.vou.events.common;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherId_Quantity {
    private String voucherId;
    private int quantity;
}
