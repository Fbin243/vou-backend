package com.vou.statistics.dto;

import java.util.ArrayList;
import java.util.List;

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
public class Player_ItemQuantitiesDto {
    private String playerId;
    private List<ItemId_Quantity> items_quantities;

    // public Player_ItemQuantitiesDto(List<ItemId_Quantity> itemsQuantities) {
    //     items_quantities = itemsQuantities;
    // }

    // getPlayerItems
    public List<PlayerItemDto> getPlayerItems () {
        List<PlayerItemDto> playerItems = new ArrayList<PlayerItemDto>();
        for (ItemId_Quantity item_quantity : items_quantities) {
            PlayerItemDto playerItem = new PlayerItemDto(playerId, item_quantity.getItemId(), item_quantity.getQuantity());
            playerItems.add(playerItem);
        }
        return playerItems;
    }
}
