package com.vou.events.common;

import com.vou.events.dto.ItemDto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemQuantity {
    private ItemDto item;
    private int quantity;
}
