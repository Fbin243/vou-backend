package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.vou.events.common.VoucherId_Quantity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddVouchersRequestDto {
    private String eventId;
    private List<VoucherId_Quantity> voucherId_Quantities;
}