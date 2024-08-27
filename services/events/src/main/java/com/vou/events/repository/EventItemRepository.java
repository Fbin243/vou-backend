package com.vou.events.repository;

import com.vou.events.entity.EventItem;
import com.vou.events.model.EventItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventItemRepository extends JpaRepository<EventItem, EventItemId>, EventItemRepositoryCustom {
    EventItem findByEventAndItem(String eventId, String itemId);
}