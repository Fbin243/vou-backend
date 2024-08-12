package com.vou.events.service;

import com.vou.events.common.GameId_StartTime;
import com.vou.events.common.ItemId_Quantity;
import com.vou.events.common.VoucherId_Quantity;
import com.vou.events.common.VoucherQuantity;
import com.vou.events.dto.EventDto;
import com.vou.events.dto.EventRegistrationInfoDto;
import com.vou.pkg.dto.ResponseDto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing events.
 */
public interface IEventsService {

    /**
     * Fetches all events.
     *
     * @return a list of all events
     */
    default List<EventDto> fetchAllEvents() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events that are currently in progress.
     *
     * @return a list of all events in progress
     */
    default List<EventDto> fetchEventsInProgress() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events filtered by date.
     *
     * @return a list of events filtered by date
     */
    default List<EventDto> fetchEventsByDate(LocalDateTime specificDate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events filtered by voucher.
     *
     * @return a list of events filtered by voucher
     */
    default List<EventDto> fetchEventsByVoucher() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events filtered by item.
     *
     * @return a list of events filtered by item
     */
    default List<EventDto> fetchEventsByItem() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events filtered by brand.
     *
     * @return a list of events filtered by brand
     */
    default List<EventDto> fetchEventsByBrand() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all events filtered by game.
     *
     * @return a list of events filtered by game
     */
    default List<EventDto> fetchEventsByGame() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches an event by its ID.
     *
     * @param id the ID of the event
     * @return the event with the specified ID
     */
    default EventDto fetchEventById(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new event.
     *
     * @param eventDto the DTO of the event to create
     * @return the created event id
     */
    String createEvent(EventDto eventDto);

    /**
     * Updates an existing event.
     *
     * @param eventDto the DTO of the event to update
     * @return true if the event was updated successfully, false otherwise
     */
    boolean updateEvent(EventDto eventDto);

    /**
     * Deletes an event by its ID.
     *
     * @param id the ID of the event to delete
     * @return true if the event was deleted successfully, false otherwise
     */
    boolean deleteEvent(String id);

    /**
     * Adds many brands to an event.
     *
     * @param eventId the ID of the event
     * @param brandIds the IDs of the brands to add
     * @return true if the brand was added successfully, false otherwise
     */
    default boolean addBrandsToEvent(String eventId, List<String> brandIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes many brands from an event.
     *
     * @param eventId the ID of the event
     * @param brandIds the IDs of the brands to remove
     * @return true if the brand was removed successfully, false otherwise
     */
    default boolean removeBrandsFromEvent(String eventId, List<String> brandIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds a voucher to an event.
     *
     * @param eventId the ID of the event
     * @param voucherIds the IDs of the vouchers to add
     * @return true if the voucher was added successfully, false otherwise
     */
    default boolean addVouchersToEvent(String eventId, List<VoucherId_Quantity> voucherIds_quantities) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes many vouchers from an event.
     *
     * @param eventId the ID of the event
     * @param voucherIds the IDs of the vouchers to add
     * @return true if the voucher was removed successfully, false otherwise
     */
    default boolean removeVouchersFromEvent(String eventId, List<String> voucherIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds items to an event.
     *
     * @param eventId the ID of the event
     * @param itemIds the IDs of the items to add
     * @return true if the item was added successfully, false otherwise
     */
    default boolean addItemsToEvent(String eventId, List<ItemId_Quantity> itemIds_quantities) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes items from an event.
     *
     * @param eventId the ID of the event
     * @param itemIds the IDs of the items to remove
     * @return true if the item was removed successfully, false otherwise
     */
    default boolean removeItemsFromEvent(String eventId, List<String> itemIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds games to an event.
     *
     * @param eventId the ID of the event
     * @param gameIds the IDs of the games to add
     * @return true if the game was added successfully, false otherwise
     */
    default boolean addGamesToEvent(String eventId, List<GameId_StartTime> listGameId_StartTime) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes games from an event.
     *
     * @param eventId the ID of the event
     * @param gameIds the IDs of the games to remove
     * @return true if the game was removed successfully, false otherwise
     */
    default boolean removeGamesFromEvent(String eventId, List<Long> gameIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates an event with session info.
     *
     * @param eventRegistrationInfoDto the DTO of the event registration info
     * @return true if the event was created successfully, false otherwise
     */
    default ResponseEntity<ResponseDto> createEventWithSessionInfo(EventRegistrationInfoDto eventRegistrationInfoDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}