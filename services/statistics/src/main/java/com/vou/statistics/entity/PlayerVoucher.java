package com.vou.statistics.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

import org.bson.types.ObjectId;

import jakarta.persistence.Id;

@Document(collection = "players_vouchers")
@Data
public class PlayerVoucher {

    @Id
    private ObjectId id;

    @Field(value = "player_id")
    private String playerId;

    @Field(value = "voucher_id")
    private String voucherId;

    @Field(value = "brand_id")
    private String brandId;

    @Field(value = "voucher_name")
    private String voucherName;

    @Field(value = "quantity")
    private int quantity;
}
