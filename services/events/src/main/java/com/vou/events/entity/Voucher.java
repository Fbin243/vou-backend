package com.vou.events.entity;

import com.vou.events.common.VoucherStatus;
import com.vou.events.common.VoucherUnitValue;
import com.vou.pkg.entity.Base;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Voucher extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // @ManyToOne
    // @JoinColumn(name = "brand_id", nullable = false)
    // private Brand brand;

    @Column(name = "brand_id")
    private String brand_id;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "image")
    private String image;

    @Column(name = "value")
    private double value;

    @Column(name = "description")
    private String description;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Column(name = "status")
    private VoucherStatus status;

    @Column(name = "unit_value")
    private VoucherUnitValue unitValue;
}