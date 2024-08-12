package com.vou.events.dto;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.dto.EventDto;
import com.vou.events.dto.VoucherDto;

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
public class EventVoucherDto {
    private EventDto event;

    private VoucherDto voucher;

    private int numberOfVoucher;

    private EventIntermediateTableStatus activeStatus;
}