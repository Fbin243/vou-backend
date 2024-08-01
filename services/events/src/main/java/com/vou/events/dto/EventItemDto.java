package com.vou.events.dto;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.dto.EventDto;
import com.vou.events.dto.ItemDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventItemDto {
    private EventDto event;

    private ItemDto item;

    private int numberOfItem;

    private EventIntermediateTableStatus activeStatus;
}