package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.Event;

public interface EventRepositoryCustom {
    List<Event> findByIds(List<String> ids);
}
