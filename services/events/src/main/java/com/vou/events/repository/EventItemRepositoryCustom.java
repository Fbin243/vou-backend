package com.vou.events.repository;

import com.vou.events.entity.EventItem;

public interface EventItemRepositoryCustom {
    EventItem findByEventAndItem(String eventId, String itemId);
}