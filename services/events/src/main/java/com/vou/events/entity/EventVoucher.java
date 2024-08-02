package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.entity.Event;
import com.vou.events.entity.Voucher;
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
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Voucher voucher;

    private int numberOfVoucher;

    private EventIntermediateTableStatus activeStatus;
}