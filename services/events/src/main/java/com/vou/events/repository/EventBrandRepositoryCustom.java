package com.vou.events.repository;

import com.vou.events.entity.EventBrand;

public interface EventBrandRepositoryCustom {
    EventBrand findByEventAndBrand(String eventId, String brandId);
}