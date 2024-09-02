package com.vou.events.repository;

import java.util.Map;

import com.vou.events.entity.VoucherItem;

public interface VoucherItemRepositoryCustom {
    VoucherItem findByVoucherAndItem(String voucherId, String itemId);
    Map<String, Integer> getItemsQuantitiesByVoucher(String voucherId);
}
