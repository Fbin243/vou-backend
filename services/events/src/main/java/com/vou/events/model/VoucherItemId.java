package com.vou.events.model;

import com.vou.events.entity.Voucher;
import com.vou.events.entity.Item;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class VoucherItemId implements Serializable {
    private Voucher voucher;

    private Item item;

    // equals() and hashCode()
}