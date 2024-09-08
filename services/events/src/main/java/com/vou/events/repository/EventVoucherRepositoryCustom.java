package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.EventVoucher;

public interface EventVoucherRepositoryCustom {
    EventVoucher findByEventAndVoucher(String eventId, String voucherId);
    List<EventVoucher> findByEvent(String voucherId);
}