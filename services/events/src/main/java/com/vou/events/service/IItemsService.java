package com.vou.events.service;

import com.vou.events.dto.ItemDto;
import com.vou.events.dto.ReturnItemDto;

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
     * Fetches items by their ids.
     *  
     * @param ids the ids of the items to fetch
     * @return a list of fetched items
     */
    default List<ItemDto> fetchItemsByIds(List<String> ids) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all items by brand.
     *  
     * @param brandId the id of the brand to fetch items for
     * @return a list of items for the specified brand
     */
    default List<ItemDto> fetchItemsByBrand(String brandId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all items by brands.
     *  
     * @param brandIds the ids of the brands to fetch items for
     * @return a list of items for the specified brands
     */
    default List<ItemDto> fetchItemsByBrands(List<String> brandIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default List<ReturnItemDto> fetchItemsByVoucher(String voucherId) {
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