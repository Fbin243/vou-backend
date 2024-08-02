package com.vou.events.mapper;

import com.vou.events.dto.VoucherItemDto;
import com.vou.events.entity.VoucherItem;

import org.springframework.stereotype.Service;

@Service
public class VoucherItemMapper {

    // Convert VoucherItem to VoucherItemDto
    public static VoucherItemDto toDto(VoucherItem voucherItem) {
        if (voucherItem == null) {
            return null;
        }

        VoucherItemDto voucherItemDto = new VoucherItemDto();
        voucherItemDto.setVoucher(VoucherMapper.toDto(voucherItem.getVoucher()));
        voucherItemDto.setItem(ItemMapper.toDto(voucherItem.getItem()));

        return voucherItemDto;
    }

    // Convert VoucherItemDto to VoucherItem
    public static VoucherItem toEntity(VoucherItemDto dto) {
        if (dto == null) {
            return null;
        }

        VoucherItem voucherItem = new VoucherItem();
        voucherItem.setVoucher(VoucherMapper.toEntity(dto.getVoucher()));
        voucherItem.setItem(ItemMapper.toEntity(dto.getItem()));

        return voucherItem;
    }
}