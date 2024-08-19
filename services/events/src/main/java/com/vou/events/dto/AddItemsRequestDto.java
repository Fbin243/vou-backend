package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.vou.events.common.ItemId_Quantity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddItemsRequestDto {
    private String eventId;
    private List<ItemId_Quantity> itemId_Quantities;
}