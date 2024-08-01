package com.vou.events.mapper;

import com.vou.events.dto.BrandDto;
import com.vou.events.entity.Brand;

import org.springframework.stereotype.Service;

@Service
public class BrandMapper {

    // Convert Brand to BrandDto
    public static BrandDto toDto(Brand brand) {
        if (brand == null) {
            return null;
        }

        BrandDto brandDto = new BrandDto();
        brandDto.setBrandName(brand.getBrandName());
        brandDto.setField(brand.getField());
        brandDto.setAddress(brand.getAddress());
        brandDto.setLatitude(brand.getLatitude());
        brandDto.setLongitude(brand.getLongitude());
        brandDto.setStatus(brand.getStatus());

        return brandDto;
    }

    // Convert BrandDto to Brand
    public static Brand toEntity(BrandDto dto) {
        if (dto == null) {
            return null;
        }

        Brand brand = new Brand();
        brand.setBrandName(dto.getBrandName());
        brand.setField(dto.getField());
        brand.setAddress(dto.getAddress());
        brand.setLatitude(dto.getLatitude());
        brand.setLongitude(dto.getLongitude());
        brand.setStatus(dto.getStatus());

        return brand;
    }
}