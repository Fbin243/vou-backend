package com.vou.events.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.pkg.dto.ResponseDto;
import com.vou.events.common.ItemQuantity;
import com.vou.events.common.VoucherQuantity;
import com.vou.events.dto.EventDto;
import com.vou.events.service.IEventsService;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventsController {
    private final IEventsService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> eventDtos = eventService.fetchAllEvents();
        return ResponseEntity.ok(eventDtos);
    }

    @GetMapping("/inProgress")
    public ResponseEntity<List<EventDto>> getEventsInProgress() {
        List<EventDto> eventDtos = eventService.fetchEventsInProgress();
        return ResponseEntity.ok(eventDtos);
    }

    @GetMapping("/date/{specificDate}")
    public ResponseEntity<List<EventDto>> getEventsByDate(@PathVariable LocalDateTime specificDate) {
        List<EventDto> eventDtos = eventService.fetchEventsByDate(specificDate);
        return ResponseEntity.ok(eventDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable String id) {
        EventDto eventDto = eventService.fetchEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Event created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateEvent(@RequestBody EventDto eventDto) {
        eventService.updateEvent(eventDto);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Event updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Event deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/brands")
    public ResponseEntity<ResponseDto> addBrandsToEvent(@RequestParam String eventId, @RequestParam List<String> brandIds) {
        eventService.addBrandsToEvent(eventId, brandIds);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Brands added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/brands")
    public ResponseEntity<ResponseDto> removeBrandsFromEvent(@RequestParam String eventId, @RequestParam List<String> brandIds) {
        eventService.removeBrandsFromEvent(eventId, brandIds);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Brands removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/vouchers")
    public ResponseEntity<ResponseDto> addVouchersToEvent(@RequestParam String eventId, @RequestParam List<VoucherQuantity> voucherIds_quantities) {
        eventService.addVouchersToEvent(eventId, voucherIds_quantities);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Vouchers added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/vouchers")
    public ResponseEntity<ResponseDto> removeVouchersFromEvent(@RequestParam String eventId, @RequestParam List<String> voucherIds) {
        eventService.removeVouchersFromEvent(eventId, voucherIds);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Vouchers removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/items")
    public ResponseEntity<ResponseDto> addItemsToEvent(@RequestParam String eventId, @RequestParam List<ItemQuantity> itemIds_quantities) {
        eventService.addItemsToEvent(eventId, itemIds_quantities);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Items added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/items")
    public ResponseEntity<ResponseDto> removeItemsFromEvent(@RequestParam String eventId, @RequestParam List<String> itemIds) {
        eventService.removeItemsFromEvent(eventId, itemIds);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Items removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}