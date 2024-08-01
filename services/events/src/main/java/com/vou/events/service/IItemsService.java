package com.vou.events.service;

import com.vou.events.dto.ItemDto;

import java.util.List;

/**
 * Service interface for managing events.
 */
public interface IItemsService {

    /**
     * Fetches all items.
     *
     * @return a list of all items
     */
    default List<ItemDto> fetchAllItems() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches an item by its id.
     *  
     * @param itemId the id of the item to fetch
     * @return the fetched item
     */
    default ItemDto fetchItemById(String itemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new item.
     *  
     * @param itemDto the item to create
     * @return the created item
     */
    default ItemDto createItem(ItemDto itemDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Updates an existing item.
     *  
     * @param itemDto the item to update
     * @return the updated item
     */
    default boolean updateItem(ItemDto itemDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Deletes an existing item.
     *  
     * @param itemId the id of the item to delete
     */
    default boolean deleteItem(String itemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}