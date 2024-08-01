package com.vou.events.repository;

import com.vou.events.entity.EventVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventVoucherRepository extends JpaRepository<EventVoucher, Long> {
    EventVoucher findByEventAndVoucher(String eventId, String voucherId);
}