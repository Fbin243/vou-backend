package com.vou.events.service;

import com.vou.events.dto.BrandDto;
import com.vou.events.dto.EventDto;
import com.vou.events.dto.EventRegistrationInfoDto;
import com.vou.events.dto.GameDto;
import com.vou.events.dto.ItemDto;
import com.vou.events.dto.VoucherDto;
import com.vou.events.mapper.GameMapper;
import com.vou.events.model.EventSessionInfo;
import com.vou.events.model.NotificationInfo;
import com.vou.events.entity.*;
import com.vou.events.mapper.BrandMapper;
import com.vou.events.mapper.EventMapper;
import com.vou.events.repository.*;
import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.common.GameId_StartTime;
import com.vou.events.common.ItemId_Quantity;
import com.vou.events.common.UserRole;
import com.vou.events.common.VoucherId_Quantity;
import com.vou.events.common.VoucherId_Quantity_ItemIds_Quantities;
import com.vou.events.client.GamesServiceClient;
import com.vou.events.client.UsersServiceClient;
import com.vou.pkg.dto.ResponseDto;
import com.vou.pkg.exception.NotFoundException;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventsService implements IEventsService {

    private static final Logger log = LoggerFactory.getLogger(EventsService.class);
    private final EventRepository           eventsRepository;
    private final BrandRepository           brandRepository;
    private final VoucherRepository         voucherRepository;
    private final ItemRepository            itemRepository;
    private final EventBrandRepository      eventBrandRepository;
    private final EventVoucherRepository    eventVoucherRepository;
    private final EventItemRepository       eventItemRepository;
    private final EventGameRepository       eventGameRepository;
    private final GamesServiceClient        gamesServiceClient;
    private final UsersServiceClient        usersServiceClient;
    private final IVouchersService          voucherService;
    private final IItemsService             itemService;

    // Properties props = new Properties();

    // KafkaProducer<String, EventSessionInfo> producer = new KafkaProducer<>(props);
    @Autowired
    private KafkaTemplate<String, EventSessionInfo> kafkaTemplateEventSessionInfo;

    // @Autowired
    // private KafkaTemplate<String, NotificationInfo> kafkaTemplateNotificationInfo;

    @Override
    public List<EventDto> fetchAllEvents() {
        return eventsRepository.findAll().stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> fetchEventsInProgress() {
        return eventsRepository.findAll().stream()
                .filter(event -> event.getStartDate().isBefore(LocalDateTime.now()) && event.getEndDate().isAfter(LocalDateTime.now()))
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> fetchEventsByDate(LocalDateTime specificDate) {
        return eventsRepository.findAll().stream()
                .filter(event -> event.getStartDate().isBefore(specificDate) && event.getEndDate().isAfter(specificDate))
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    // @Override
    // public List<EventDto> fetchEventsByVoucher() {
    //     return eventsRepository.findAll().stream()
    //             .sorted(Comparator.comparing(Event::getNumberOfVoucher).reversed())
    //             .map(EventMapper::toDto)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public List<EventDto> fetchEventsByItem() {
    //     return eventsRepository.findAll().stream()
    //             .sorted(Comparator.comparing(Event::getName))
    //             .map(EventMapper::toDto)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public List<EventDto> fetchEventsByBrand() {
    //     return eventsRepository.findAll().stream()
    //             .sorted(Comparator.comparing(Event::getBrand))
    //             .map(EventMapper::toDto)
    //             .collect(Collectors.toList());
    // }

    @Override
    public EventDto fetchEventById(String id) {
        Event event = eventsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event", "id", id)
        );
        return EventMapper.toDto(event);
    }

    @Override
    public String createEvent(EventDto eventDto) {
        Event event = EventMapper.toEntity(eventDto);
        event.setId(null);
        Event createdEvent = eventsRepository.save(event);
        return createdEvent.getId().toString();
    }

    @Override
    public boolean updateEvent(EventDto eventDto) {
        try {
            Event event = eventsRepository.findById(eventDto.getId()).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventDto.getId())
            );

            event.setName(eventDto.getName());
            event.setStartDate(eventDto.getStartDate());
            event.setEndDate(eventDto.getEndDate());
            event.setNumberOfVoucher(eventDto.getNumberOfVoucher());
            event.setImage(eventDto.getImage());

            eventsRepository.save(event);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteEvent(String id) {
        try {
            Event event = eventsRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Event", "id", id)
            );

            eventsRepository.delete(event);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addBrandsToEvent(String eventId, List<String> brandIds) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String brandId : brandIds) {
                Brand brand = brandRepository.findById(brandId).orElseThrow(
                        () -> new NotFoundException("Brand", "id", brandId)
                );

                EventBrand eventBrand = eventBrandRepository.findByEventAndBrand(eventId, brandId);
                if (eventBrand != null) {
                    eventBrand.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventBrandRepository.save(eventBrand);
                }
                else {
                    eventBrand = new EventBrand();
                    eventBrand.setEvent(event);
                    eventBrand.setBrand(brand);
                    eventBrand.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventBrandRepository.save(eventBrand);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addBrandsByEmailsToEvent(String eventId, List<String> emails) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            List<BrandDto> brands = usersServiceClient.getBrandsByEmails(emails);
            log.info("BRAND: {}", brands);
            // // don't need Thang to test
            // brands = new ArrayList<>();
            // brands.add(new BrandDto());
            // brands.get(0).setId("81ba2eb1-0311-4c44-bc11-3b94f1cadd62");
            // brands.get(0).setAccountId("d1ae9e33-9c2e-4f53-8f91-9d23d6d933b1");

            if (brands == null || brands.isEmpty()) {
                log.info("BRAND IS NULL");
                return false;
            } else {
                for (BrandDto _brandDto : brands) {
                    Brand _brandEntity = BrandMapper.toEntity(_brandDto);
                    EventBrand eventBrand = eventBrandRepository.findByEventAndBrand(eventId, _brandEntity.getId());
                    if (eventBrand != null) {
                        eventBrand.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                        eventBrandRepository.save(eventBrand);
                    }
                    else {
                        eventBrand = new EventBrand();
                        eventBrand.setEvent(event);
                        eventBrand.setBrand(_brandEntity);
                        eventBrand.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                        eventBrandRepository.save(eventBrand);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean removeBrandsFromEvent(String eventId, List<String> brandIds) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String brandId : brandIds) {
                Brand brand = brandRepository.findById(brandId).orElseThrow(
                        () -> new NotFoundException("Brand", "id", brandId)
                );

                EventBrand eventBrand = eventBrandRepository.findByEventAndBrand(eventId, brandId);
                if (eventBrand != null) {
                    eventBrand.setActiveStatus(EventIntermediateTableStatus.INACTIVE);
                    eventBrandRepository.save(eventBrand);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addVouchersToEvent(String eventId, List<VoucherId_Quantity> voucherIds_quantities) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (VoucherId_Quantity voucherIds_quantity : voucherIds_quantities) {
                Voucher voucher = voucherRepository.findById(voucherIds_quantity.getVoucherId()).orElseThrow(
                        () -> new NotFoundException("Voucher", "id", voucherIds_quantity.getVoucherId())
                );

                EventVoucher eventVoucher = eventVoucherRepository.findByEventAndVoucher(eventId, voucherIds_quantity.getVoucherId());
                if (eventVoucher != null) {
                    eventVoucher.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventVoucher.setNumberOfVoucher(voucherIds_quantity.getQuantity());
                    eventVoucherRepository.save(eventVoucher);
                }
                else {
                    eventVoucher = new EventVoucher();
                    eventVoucher.setEvent(event);
                    eventVoucher.setVoucher(voucher);
                    eventVoucher.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventVoucher.setNumberOfVoucher(voucherIds_quantity.getQuantity());
                    eventVoucherRepository.save(eventVoucher);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean removeVouchersFromEvent(String eventId, List<String> voucherIds) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String voucherId : voucherIds) {
                Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(
                        () -> new NotFoundException("Voucher", "id", voucherId)
                );

                EventVoucher eventVoucher = eventVoucherRepository.findByEventAndVoucher(eventId, voucherId);
                if (eventVoucher != null) {
                    eventVoucher.setActiveStatus(EventIntermediateTableStatus.INACTIVE);
                    eventVoucherRepository.save(eventVoucher);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addItemsToEvent(String eventId, List<ItemId_Quantity> itemIds_quantities) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (ItemId_Quantity itemIds_quantity : itemIds_quantities) {
                Item item = itemRepository.findById(itemIds_quantity.getItemId()).orElseThrow(
                    () -> new NotFoundException("Item", "id", itemIds_quantity.getItemId())
                );

                EventItem eventItem = eventItemRepository.findByEventAndItem(eventId, itemIds_quantity.getItemId());
                if (eventItem != null) {
                    eventItem.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventItem.setNumberOfItem(itemIds_quantity.getQuantity());
                    eventItemRepository.save(eventItem);
                }
                else {
                    eventItem = new EventItem();
                    eventItem.setEvent(event);
                    eventItem.setItem(item);
                    eventItem.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventItem.setNumberOfItem(itemIds_quantity.getQuantity());
                    eventItemRepository.save(eventItem);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean removeItemsFromEvent(String eventId, List<String> itemIds) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String itemId : itemIds) {
                Item item = itemRepository.findById(itemId).orElseThrow(
                        () -> new NotFoundException("Item", "id", itemId)
                );

                EventItem eventItem = eventItemRepository.findByEventAndItem(eventId, itemId);
                if (eventItem != null) {
                    eventItem.setActiveStatus(EventIntermediateTableStatus.INACTIVE);
                    eventItemRepository.save(eventItem);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    @Override
    public boolean addGamesToEvent(String eventId, List<GameId_StartTime> listGameId_StartTime) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (GameId_StartTime gameId_startTime : listGameId_StartTime) {
                GameDto gameDto = gamesServiceClient.getGameById(gameId_startTime.getGameId());
                Game game = GameMapper.toEntity(gameDto);
                if (game == null) {
                    throw new NotFoundException("Game", "id", gameId_startTime.getGameId().toString());
                }
                
                EventGame eventGame = eventGameRepository.findByEventAndGame(eventId, gameId_startTime.getGameId());
                
                if (eventGame != null) {
                    eventGame.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventGame.setStartTime(gameId_startTime.getStartTime());
                    eventGameRepository.save(eventGame);
                }
                else {
                    eventGame = new EventGame();
                    eventGame.setEvent(event);
                    eventGame.setGame(game);
                    eventGame.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    eventGame.setStartTime(gameId_startTime.getStartTime());
                    eventGameRepository.save(eventGame);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    @Override
    public boolean removeGamesFromEvent(String eventId, List<Long> gameIds) {
        try {
            Event event = eventsRepository.findById(eventId).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (Long gameId : gameIds) {
                GameDto gameDto = gamesServiceClient.getGameById(gameId);
                Game game = GameMapper.toEntity(gameDto);
                if (game == null) {
                    throw new NotFoundException("Game", "id", gameId.toString());
                }

                EventGame eventGame = eventGameRepository.findByEventAndGame(eventId, gameId);
                if (eventGame != null) {
                    eventGame.setActiveStatus(EventIntermediateTableStatus.INACTIVE);
                    eventGameRepository.save(eventGame);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    @Override
    public ResponseEntity<ResponseDto> createEventWithSessionInfo(EventRegistrationInfoDto eventRegistrationInfoDto) {
        VoucherDto                  existVoucher;
        ItemDto                     existItem;
        String                      voucherId;
        String                      itemId;
        EventDto                    existEvent                      = eventRegistrationInfoDto.getEvent();
        List<VoucherId_Quantity>    listVoucherIdQuantity           = new ArrayList<>();
        List<ItemId_Quantity>       listItemIdQuantity              = new ArrayList<>();
        String                      regexSplitStringLocalDateTime   = "T";
        Integer                     sumNumberOfVoucher              = 0;
        String                      eventId                         = null;
        if (existEvent != null)     eventId                         = this.createEvent(existEvent);

        if (eventId == null) {
            ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Event creation failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } else {
            if (!this.addBrandsByEmailsToEvent(eventId, eventRegistrationInfoDto.getEmails())) {
                ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Brand addition failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
            }

            for (VoucherId_Quantity_ItemIds_Quantities listVoucher_Items : eventRegistrationInfoDto.getListVoucher_Items()) {
                voucherId       = listVoucher_Items.getVoucherId();
                existVoucher    = voucherService.fetchVoucherById(voucherId);

                if (existVoucher == null) {
                    ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Voucher creation failed.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
                } else {
                    listVoucherIdQuantity.add(new VoucherId_Quantity(voucherId, listVoucher_Items.getQuantityOfVoucher()));
                    listItemIdQuantity.clear();

                    for (ItemId_Quantity itemId_Quantity : listVoucher_Items.getItemIds_quantities()) {
                        itemId      = itemId_Quantity.getItemId();
                        existItem   = itemService.fetchItemById(itemId);

                        if (existItem == null) {
                            ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Item creation failed.");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
                        } else {
                            listItemIdQuantity.add(new ItemId_Quantity(itemId, itemId_Quantity.getQuantity()));
                        }
                    }
                    if (listItemIdQuantity.size() > 0) {
                        if (!voucherService.addVoucherItemConversion(voucherId, listItemIdQuantity)) {
                            ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Voucher-Item conversion failed.");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
                        }
                        if (!this.addItemsToEvent(eventId, listItemIdQuantity)) {
                            ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Voucher addition failed.");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
                        }

                        sumNumberOfVoucher += listVoucher_Items.getQuantityOfVoucher();
                    }
                }
            }

            if (!this.addVouchersToEvent(eventId, listVoucherIdQuantity)) {
                ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Voucher addition failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
            }
            
            if (this.addGamesToEvent(eventId, eventRegistrationInfoDto.getListGameId_StartTime())) {

                // Update sum(number of voucher)
                existEvent.setId(eventId);
                existEvent.setNumberOfVoucher(sumNumberOfVoucher);
                if (!this.updateEvent(existEvent)) {
                    ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Sum of number of voucher for this event updating failed.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
                }

                // Send list of games to Kafka - topic = event-session
                try {
                    for (GameId_StartTime gameId_StartTime : eventRegistrationInfoDto.getListGameId_StartTime()) {
                        EventSessionInfo eventSessionInfo = new EventSessionInfo(
                            eventId,
                            gameId_StartTime.getGameId().toString(),
                            eventRegistrationInfoDto.getEvent().getStartDate().toString().split(regexSplitStringLocalDateTime)[0],
                            eventRegistrationInfoDto.getEvent().getEndDate().toString().split(regexSplitStringLocalDateTime)[0],
                            gameId_StartTime.getStartTime().toString(),
                            gameId_StartTime.getStartTime().plusMinutes(30).toString()
                        );
                
                        kafkaTemplateEventSessionInfo.send("event-session", eventSessionInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // // wair for session response
                // // Send notification to related brands
                // try {
                //     kafkaTemplateNotificationInfo.send("event-notification", new NotificationInfo("NotificationId", "NotificationTitle", "NotificationDescription", "NotificationImageUrl"));
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

            } else {
                ResponseDto res = new ResponseDto(HttpStatus.BAD_REQUEST, "Game addition failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
            }
        }

        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Event created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}