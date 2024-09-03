package com.vou.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.vou.pkg.dto.ResponseDto;
import com.vou.events.dto.ItemDto;
import com.vou.events.service.IItemsService;
import com.vou.events.dto.ReturnItemDto;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/items", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ItemsController {
    private final IItemsService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> itemDtos = itemService.fetchAllItems();
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable String id) {
        ItemDto itemDto = itemService.fetchItemById(id);
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<ItemDto>> getItemsByIds(@RequestBody List<String> ids) {
        List<ItemDto> itemDtos = itemService.fetchItemsByIds(ids);
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<ItemDto>> getItemsByBrand(@PathVariable String brandId) {
        List<ItemDto> itemDtos = itemService.fetchItemsByBrand(brandId);
        return ResponseEntity.ok(itemDtos);
    }

    @PostMapping("/brands")
    public ResponseEntity<List<ItemDto>> getItemsByBrands(@RequestBody List<String> brandIds) {
        List<ItemDto> itemDtos = itemService.fetchItemsByBrands(brandIds);
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<ReturnItemDto>> getItemsByVoucher(@PathVariable String voucherId) {
        List<ReturnItemDto> itemDtos = itemService.fetchItemsByVoucher(voucherId);
        return ResponseEntity.ok(itemDtos);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        ItemDto createdItem = itemService.createItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateItem(@RequestBody ItemDto itemDto) {
        boolean updated = itemService.updateItem(itemDto);
        if (updated) {
            ResponseDto res = new ResponseDto(HttpStatus.OK, "Item updated successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            ResponseDto res = new ResponseDto(HttpStatus.NOT_FOUND, "Item not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteItem(@PathVariable String id) {
        boolean deleted = itemService.deleteItem(id);
        if (deleted) {
            ResponseDto res = new ResponseDto(HttpStatus.OK, "Item deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            ResponseDto res = new ResponseDto(HttpStatus.NOT_FOUND, "Item not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }
}
