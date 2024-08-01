package com.vou.events.mapper;

import com.vou.events.dto.ItemDto;
import com.vou.events.entity.Item;
import com.vou.events.mapper.BrandMapper;

import org.springframework.stereotype.Service;

@Service
public class ItemMapper {

    // Convert Item to ItemDto
    public static ItemDto toDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemDto itemDto = new ItemDto();
        itemDto.setBrand(BrandMapper.toDto(item.getBrand()));
        itemDto.setName(item.getName());
        itemDto.setIcon(item.getIcon());
        itemDto.setDescription(item.getDescription());

        return itemDto;
    }

    // Convert ItemDto to Item
    public static Item toEntity(ItemDto dto) {
        if (dto == null) {
            return null;
        }

        Item item = new Item();
        item.setBrand(BrandMapper.toEntity(dto.getBrand()));
        item.setName(dto.getName());
        item.setIcon(dto.getIcon());
        item.setDescription(dto.getDescription());

        return item;
    }
}
