package com.vou.events.mapper;

import com.vou.events.dto.EventBrandDto;
import com.vou.events.entity.EventBrand;

import org.springframework.stereotype.Service;

@Service
public class EventBrandMapper {

    // Convert EventBrand to EventBrandDto
    public static EventBrandDto toDto(EventBrand eventBrand) {
        if (eventBrand == null) {
            return null;
        }

        EventBrandDto eventBrandDto = new EventBrandDto();
        eventBrandDto.setEvent(EventMapper.toDto(eventBrand.getEvent()));
        eventBrandDto.setBrand(BrandMapper.toDto(eventBrand.getBrand()));

        return eventBrandDto;
    }

    // Convert EventBrandDto to EventBrand
    public static EventBrand toEntity(EventBrandDto dto) {
        if (dto == null) {
            return null;
        }

        EventBrand eventBrand = new EventBrand();
        eventBrand.setEvent(EventMapper.toEntity(dto.getEvent()));
        eventBrand.setBrand(BrandMapper.toEntity(dto.getBrand()));

        return eventBrand;
    }
}