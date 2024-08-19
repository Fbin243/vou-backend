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
        brandDto.setId(brand.getId());
        brandDto.setFullName(brand.getFullName());
        brandDto.setUsername(brand.getUsername());
        brandDto.setPassword(brand.getPassword());
        brandDto.setEmail(brand.getEmail());
        brandDto.setPhone(brand.getPhone());
        brandDto.setRole(brand.getRole());
        brandDto.setBrandName(brand.getBrandName());
        brandDto.setField(brand.getField());
        brandDto.setAddress(brand.getAddress());
        brandDto.setLatitude(brand.getLatitude());
        brandDto.setLongitude(brand.getLongitude());
        brandDto.setStatus(brand.isStatus());

        return brandDto;
    }

    // Convert BrandDto to Brand
    public static Brand toEntity(BrandDto dto) {
        if (dto == null) {
            return null;
        }

        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setFullName(dto.getFullName());
        brand.setUsername(dto.getUsername());
        brand.setPassword(dto.getPassword());
        brand.setEmail(dto.getEmail());
        brand.setPhone(dto.getPhone());
        brand.setRole(dto.getRole());
        brand.setBrandName(dto.getBrandName());
        brand.setField(dto.getField());
        brand.setAddress(dto.getAddress());
        brand.setLatitude(dto.getLatitude());
        brand.setLongitude(dto.getLongitude());
        brand.setStatus(dto.isStatus());

        return brand;
    }
}