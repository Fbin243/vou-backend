package com.vou.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.vou.pkg.dto.ResponseDto;
import com.vou.events.dto.AddBrandsRequestDto;
import com.vou.events.dto.AddGamesRequestDto;
import com.vou.events.dto.AddItemsRequestDto;
import com.vou.events.dto.AddVouchersRequestDto;
import com.vou.events.dto.BrandWithEventActiveStatusDto;
import com.vou.events.dto.EventDto;
import com.vou.events.dto.EventId_GameIdsDto;
import com.vou.events.dto.EventId_ItemIdsDto;
import com.vou.events.dto.EventId_VoucherIdsDto;
import com.vou.events.dto.EventRegistrationInfoDto;
import com.vou.events.dto.EventWithBrandActiveStatusDto;
import com.vou.events.service.IEventsService;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EventsController {

    // @Autowired
    // private KafkaTemplate<String, EventSessionInfo> kafkaTemplate;
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

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEventWithSessionInfo(@RequestBody EventRegistrationInfoDto eventRegistrationInfoDto) {
        return eventService.createEventWithSessionInfo(eventRegistrationInfoDto);
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

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<EventWithBrandActiveStatusDto>> getEventsByBrand(@PathVariable String brandId) {
        List<EventWithBrandActiveStatusDto> eventDtos = eventService.fetchEventsByBrand(brandId);
        return ResponseEntity.ok(eventDtos);
    }

    // @GetMapping("/brands")
    // public ResponseEntity<List<BrandWithEventActiveStatusDto>> getBrandsByEvent(@RequestParam("eventId") String eventId) {
    //     List<BrandWithEventActiveStatusDto> eventDtos = eventService.fetchBrandsByEvent(eventId);
    //     return ResponseEntity.ok(eventDtos);
    // }

    @GetMapping("/brands")
    public ResponseEntity<List<EventWithBrandActiveStatusDto>> getEventsByBrands(@RequestBody List<String> brandIds) {
        List<EventWithBrandActiveStatusDto> eventDtos = eventService.fetchEventsByBrands(brandIds);
        return ResponseEntity.ok(eventDtos);
    }

    @PostMapping("/brands")
    public ResponseEntity<ResponseDto> addBrandsToEvent(@RequestBody AddBrandsRequestDto addBrandsRequestDto) {
        eventService.addBrandsToEvent(addBrandsRequestDto.getEventId(), addBrandsRequestDto.getBrandIds());
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Brands added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/brands")
    public ResponseEntity<ResponseDto> removeBrandsFromEvent(@RequestBody AddBrandsRequestDto addBrandsRequestDto) {
        eventService.removeBrandsFromEvent(addBrandsRequestDto.getEventId(), addBrandsRequestDto.getBrandIds());
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Brands removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/vouchers")
    public ResponseEntity<ResponseDto> addVouchersToEvent(@RequestBody AddVouchersRequestDto addVouchersRequestDto) {
        eventService.addVouchersToEvent(addVouchersRequestDto.getEventId(), addVouchersRequestDto.getVoucherId_Quantities());
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Vouchers added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/vouchers")
    public ResponseEntity<ResponseDto> removeVouchersFromEvent(@RequestBody EventId_VoucherIdsDto eventId_VoucherId) {
        eventService.removeVouchersFromEvent(eventId_VoucherId.getEventId(), eventId_VoucherId.getVoucherIds());
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Vouchers removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/items")
    public ResponseEntity<ResponseDto> addItemsToEvent(@RequestBody AddItemsRequestDto addItemsRequestDto) {
        eventService.addItemsToEvent(addItemsRequestDto.getEventId(), addItemsRequestDto.getItemId_Quantities());
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Items added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/items")
    public ResponseEntity<ResponseDto> removeItemsFromEvent(@RequestBody EventId_ItemIdsDto eventId_ItemIds) {
        eventService.removeItemsFromEvent(eventId_ItemIds.getEventId(), eventId_ItemIds.getItemIds());
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Items removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/games")
    public ResponseEntity<ResponseDto> addGamesToEvent(@RequestBody AddGamesRequestDto addGamesRequestDto) {
        eventService.addGamesToEvent(addGamesRequestDto.getEventId(), addGamesRequestDto.getGameId_StartTimes());
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Games added to event successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/games")
    public ResponseEntity<ResponseDto> removeGamesFromEvent(@RequestBody EventId_GameIdsDto eventId_GameIds) {
        eventService.removeGamesFromEvent(eventId_GameIds.getEventId(), eventId_GameIds.getGameIds());
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Games removed from event successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}