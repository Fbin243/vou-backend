package com.vou.events.repository;

import com.vou.events.entity.EventBrand;
import com.vou.events.model.EventBrandId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventBrandRepository extends JpaRepository<EventBrand, EventBrandId>, EventBrandRepositoryCustom {
    EventBrand findByEventAndBrand(String eventId, String brandId);
    List<EventBrand> findByBrand(String brandId);
    List<EventBrand> findByEvent(String eventId);
    List<EventBrand> findByBrands(List<String> brandIds);
}