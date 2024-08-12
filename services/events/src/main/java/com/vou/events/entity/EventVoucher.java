package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.model.EventVoucherId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "events_vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(EventVoucherId.class)
public class EventVoucher implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @Column(name = "number_of_voucher")
    private int numberOfVoucher;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private EventIntermediateTableStatus activeStatus;
}