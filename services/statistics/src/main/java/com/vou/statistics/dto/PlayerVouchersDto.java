package com.vou.statistics.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerVouchersDto {
    private String playerId;
    private List<String> voucherIds;
}
