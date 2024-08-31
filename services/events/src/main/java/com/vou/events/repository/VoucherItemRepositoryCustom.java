package com.vou.events.repository;

import com.vou.events.entity.VoucherItem;

import java.util.List;

public interface VoucherItemRepositoryCustom {
    VoucherItem findByVoucherAndItem(String voucherId, String itemId);
    List<VoucherItem> findByVoucher(String voucherId);
}
