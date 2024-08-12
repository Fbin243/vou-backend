package com.vou.events.dto;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.dto.BrandDto;
import com.vou.events.dto.EventDto;

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
public class EventBrandDto {
    private EventDto event;
    
    private BrandDto brand;

    private EventIntermediateTableStatus activeStatus;
}