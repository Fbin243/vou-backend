package com.vou.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.vou.pkg.dto.ResponseDto;
import com.vou.events.dto.ItemDto;
import com.vou.events.service.IItemsService;

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

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@RequestBody ItemDto itemDto) {
        itemService.createItem(itemDto);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Item created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
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
