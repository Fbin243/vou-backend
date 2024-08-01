package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.entity.Voucher;
import com.vou.events.entity.Item;
import com.vou.events.model.VoucherItemId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "vouchers_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(VoucherItemId.class)
public class VoucherItem implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Voucher voucher;

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Item item;

    private int numberOfItem;

    private EventIntermediateTableStatus activeStatus;
}