package com.vou.events.repository;

import com.vou.events.entity.VoucherItem;

public interface VoucherItemRepositoryCustom {
    VoucherItem findByVoucherAndItem(String voucherId, String itemId);
}
