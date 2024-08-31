package com.vou.events.repository;

import com.vou.events.entity.EventVoucher;
import com.vou.events.model.EventVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventVoucherRepository extends JpaRepository<EventVoucher, EventVoucherId>, EventVoucherRepositoryCustom {
    EventVoucher findByEventAndVoucher(String eventId, String voucherId);
    List<EventVoucher> findByEvent(String voucherId);
}