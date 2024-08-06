package com.vou.events.repository;

import com.vou.events.entity.EventVoucher;

public interface EventVoucherRepositoryCustom {
    EventVoucher findByEventAndVoucher(String eventId, String voucherId);
}