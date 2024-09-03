package com.vou.statistics.mapper;

import org.springframework.stereotype.Service;

import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.entity.PlayerVoucher;

@Service
public class PlayerVoucherMapper {
    
    // Convert PlayerVoucher to PlayerVoucherDto
    public static PlayerVoucherDto toDto(PlayerVoucher playerVoucher) {
        if (playerVoucher == null) {
            return null;
        }

        PlayerVoucherDto playerVoucherDto = new PlayerVoucherDto();
        playerVoucherDto.setPlayerId(playerVoucher.getPlayerId());
        playerVoucherDto.setVoucherId(playerVoucher.getVoucherId());
        playerVoucherDto.setQuantity(playerVoucher.getQuantity());

        return playerVoucherDto;
    }

    // Convert PlayerVoucherDto to PlayerVoucher
    public static PlayerVoucher toEntity(PlayerVoucherDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerVoucher playerVoucher = new PlayerVoucher();
        playerVoucher.setPlayerId(dto.getPlayerId());
        playerVoucher.setVoucherId(dto.getVoucherId());
        playerVoucher.setQuantity(dto.getQuantity());

        return playerVoucher;
    }
}
