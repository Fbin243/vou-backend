package com.vou.statistics.service;

import java.util.List;

import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.dto.PlayerItemsDto;
import com.vou.statistics.dto.Player_ItemQuantitiesDto;
import com.vou.statistics.entity.PlayerItem;

/**
 * Service interface for managing playeritem.
 */
public interface IPlayerItemService {

    /**
     * Get all player items by player id.
     *
     * @param playerId the player id
     * @return the list of player items
     */
    default List<PlayerItem> getByPlayerId(String playerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get all player items by item id.
     *
     * @param itemId the item id
     * @return the list of player items
     */
    default List<PlayerItem> getByItemId(String itemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get player item by player id and item id.
     *
     * @param playerId the player id
     * @param itemId the item id
     * @return the player item
     */
    default List<ItemDto> getItemsByPlayer(String playerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get player item by player id and item id.
     *
     * @param playerId the player id
     * @param itemId the item id
     * @return the player item
     */
    default List<PlayerDto> getPlayersByItem(String itemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add player item.
     *
     * @param dto the player item dto
     * @return the player item
     */
    default PlayerItem addPlayerItem(PlayerItemDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add player items.
     *
     * @param dto the player item quantities dto
     * @return the list of player items
     */
    default List<PlayerItem> addPlayerItems(Player_ItemQuantitiesDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Delete player item.
     *
     * @param dto the player item dto
     * @return true if the player item was deleted, false otherwise
     */
    default Boolean deletePlayerItem(PlayerItemDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Delete player items.
     *
     * @param dto the player items dto
     * @return true if the player items were deleted, false otherwise
     */
    default Boolean deletePlayerItems(PlayerItemsDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
