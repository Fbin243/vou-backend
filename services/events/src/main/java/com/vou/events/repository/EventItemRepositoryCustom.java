package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.EventItem;

public interface EventItemRepositoryCustom {
    EventItem findByEventAndItem(String eventId, String itemId);
    List<EventItem> findByEvent(String eventId);
}