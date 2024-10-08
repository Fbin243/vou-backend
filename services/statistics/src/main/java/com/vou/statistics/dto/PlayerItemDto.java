package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerItemDto {
    private String playerId;

    private String itemId;

    private String brandId;

    private String itemName;

    private Long gameId;

    private int quantity;
}
