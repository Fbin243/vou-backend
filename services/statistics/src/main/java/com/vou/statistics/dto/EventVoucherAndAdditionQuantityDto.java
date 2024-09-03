package com.vou.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventVoucherAndAdditionQuantityDto {
    private String eventId;
    private String voucherId;
    private int additionalQuantity;
}
