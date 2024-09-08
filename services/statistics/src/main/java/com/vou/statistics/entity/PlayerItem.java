package com.vou.statistics.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

import org.bson.types.ObjectId;

import jakarta.persistence.Id;

@Document(collection = "players_items")
@Data
public class PlayerItem {

    @Id
    private ObjectId id;

    @Field(value = "player_id")
    private String playerId;

    @Field(value = "item_id")
    private String itemId;

    @Field(value = "brand_id")
    private String brandId;

    @Field(value = "item_name")
    private String itemName;

    @Field(value = "game_id")
    private Long gameId;

    @Field(value = "quantity")
    private int quantity;
}
