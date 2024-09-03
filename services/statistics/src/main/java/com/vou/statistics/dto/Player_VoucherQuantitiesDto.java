package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player_VoucherQuantitiesDto {
    private String playerId;
    private List<VoucherId_Quantity> vouchers_quantities;

    // getPlayerVouchers
    public List<PlayerVoucherDto> getPlayerVouchers () {
        List<PlayerVoucherDto> playerVouchers = new ArrayList<PlayerVoucherDto>();
        for (VoucherId_Quantity voucher_quantity : vouchers_quantities) {
            PlayerVoucherDto playerVoucher = new PlayerVoucherDto(playerId, voucher_quantity.getVoucherId(), voucher_quantity.getQuantity());
            playerVouchers.add(playerVoucher);
        }
        return playerVouchers;
    }
}
