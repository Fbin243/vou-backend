package com.vou.events.model;

import com.vou.events.entity.Event;
import com.vou.events.entity.Voucher;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class EventVoucherId implements Serializable {
    private Event event;

    private Voucher voucher;

    // equals() and hashCode()
}