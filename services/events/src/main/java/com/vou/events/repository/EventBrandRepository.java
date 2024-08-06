package com.vou.events.repository;

import com.vou.events.entity.EventBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventBrandRepository extends JpaRepository<EventBrand, Long>, EventBrandRepositoryCustom {
    EventBrand findByEventAndBrand(String eventId, String brandId);
}