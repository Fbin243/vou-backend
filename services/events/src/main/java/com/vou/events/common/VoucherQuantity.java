package com.vou.events.common;

import com.vou.events.dto.VoucherDto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherQuantity {
    private VoucherDto voucher;
    private int quantity;
}
