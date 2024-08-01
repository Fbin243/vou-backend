package com.vou.events.repository;

import com.vou.events.entity.EventItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventItemRepository extends JpaRepository<EventItem, Long> {
    EventItem findByEventAndItem(String eventId, String itemId);
}