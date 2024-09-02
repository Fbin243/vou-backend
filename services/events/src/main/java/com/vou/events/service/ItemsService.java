package com.vou.events.service;

import com.vou.events.client.UsersServiceClient;
import com.vou.events.dto.BrandDto;
import com.vou.events.dto.ItemDto;
import com.vou.events.entity.*;
import com.vou.events.mapper.BrandMapper;
import com.vou.events.mapper.ItemMapper;
import com.vou.events.repository.*;
import com.vou.pkg.exception.NotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemsService implements IItemsService {
    
    private final ItemRepository        itemRepository;
    private final BrandRepository       brandRepository;
    private final UsersServiceClient    usersServiceClient;

    @Override
    public List<ItemDto> fetchAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto fetchItemById(String id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Item", "id", id)
        );
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> fetchItemsByIds(List<String> ids) {
        List<Item> items = itemRepository.findByIds(ids);
        return items.stream().map(ItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> fetchItemsByBrand(String brandId) {
        List<Item> items = itemRepository.findByBrand(brandId);
        return items.stream().map(ItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> fetchItemsByBrands(List<String> brandIds) {
        List<Item> items = itemRepository.findByBrands(brandIds);
        return items.stream().map(ItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto createItem(ItemDto itemDto) {

        BrandDto brandDto = usersServiceClient.getBrand(itemDto.getBrand().getId());
                     
        Item item = ItemMapper.toEntity(itemDto);
        item.setBrand(brandDto != null ? BrandMapper.toEntity(brandDto) : null);
        Item createdItem = itemRepository.save(item);
        return ItemMapper.toDto(createdItem);
    }

    @Override
    public boolean updateItem(ItemDto itemDto) {
        try {
            Item item = itemRepository.findById(itemDto.getId()).orElseThrow(
                    () -> new NotFoundException("Item", "id", itemDto.getId())
            );

            Item updatedItem = ItemMapper.toEntity(itemDto);
            updatedItem.setId(item.getId());
            itemRepository.save(updatedItem);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Item item = itemRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Item", "id", id)
            );

            itemRepository.delete(item);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}