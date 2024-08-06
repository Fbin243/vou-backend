package com.vou.events.service;

import com.vou.events.dto.EventDto;
import com.vou.events.entity.*;
import com.vou.events.mapper.EventMapper;
import com.vou.events.repository.*;
import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.common.ItemQuantity;
import com.vou.events.common.VoucherQuantity;
import com.vou.pkg.exception.NotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventsService implements IEventsService {
    
    private final EventRepository           eventsRepository;
    private final BrandRepository           brandRepository;
    private final VoucherRepository         voucherRepository;
    private final ItemRepository            itemRepository;
    private final EventBrandRepository      eventBrandRepository;
    private final EventVoucherRepository    eventVoucherRepository;
    private final EventItemRepository       eventItemRepository;

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
        Event event = eventsRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new NotFoundException("Event", "id", id)
        );
        return EventMapper.toDto(event);
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = EventMapper.toEntity(eventDto);
        Event createdEvent = eventsRepository.save(event);
        return EventMapper.toDto(createdEvent);
    }

    @Override
    public boolean updateEvent(EventDto eventDto) {
        try {
            Event event = eventsRepository.findById(Long.parseLong(eventDto.getId())).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventDto.getId())
            );

            Event updatedEvent = EventMapper.toEntity(eventDto);
            updatedEvent.setId(event.getId());
            eventsRepository.save(updatedEvent);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteEvent(String id) {
        try {
            Event event = eventsRepository.findById(Long.parseLong(id)).orElseThrow(
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
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String brandId : brandIds) {
                Brand brand = brandRepository.findById(Long.parseLong(brandId)).orElseThrow(
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
    public boolean removeBrandsFromEvent(String eventId, List<String> brandIds) {
        try {
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String brandId : brandIds) {
                Brand brand = brandRepository.findById(Long.parseLong(brandId)).orElseThrow(
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
    public boolean addVouchersToEvent(String eventId, List<VoucherQuantity> voucherIds_quantities) {
        try {
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (VoucherQuantity voucherIds_quantity : voucherIds_quantities) {
                Voucher voucher = voucherRepository.findById(Long.parseLong(voucherIds_quantity.getVoucherId())).orElseThrow(
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
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String voucherId : voucherIds) {
                Voucher voucher = voucherRepository.findById(Long.parseLong(voucherId)).orElseThrow(
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
    public boolean addItemsToEvent(String eventId, List<ItemQuantity> itemIds_quantities) {
        try {
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (ItemQuantity itemIds_quantity : itemIds_quantities) {
                Item item = itemRepository.findById(Long.parseLong(itemIds_quantity.getItemId())).orElseThrow(
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
            Event event = eventsRepository.findById(Long.parseLong(eventId)).orElseThrow(
                    () -> new NotFoundException("Event", "id", eventId)
            );

            for (String itemId : itemIds) {
                Item item = itemRepository.findById(Long.parseLong(itemId)).orElseThrow(
                        () -> new NotFoundException("Item", "id", itemId)
                );

                EventItem eventItem = eventItemRepository.findByEventAndItem(eventId, itemId);
                if (eventItem != null) {
                    eventItem.setActiveStatus(EventIntermediateTableStatus.INACTIVE);
                    eventItemRepository.save(eventItem);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}