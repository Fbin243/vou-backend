package com.vou.events.mapper;

import com.vou.events.common.VoucherUnitValue;
import com.vou.events.dto.VoucherDto;
import com.vou.events.entity.Voucher;

import org.springframework.stereotype.Service;

@Service
public class VoucherMapper {

    // Convert Voucher to VoucherDto
    public static VoucherDto toDto(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setId(voucher.getId());
        voucherDto.setBrand_id(voucher.getBrand_id());
        voucherDto.setVoucherCode(voucher.getVoucherCode());
        voucherDto.setQrCode(voucher.getQrCode());
        voucherDto.setImage(voucher.getImage());
        voucherDto.setValue(voucher.getValue());
        voucherDto.setDescription(voucher.getDescription());
        voucherDto.setExpiredDate(voucher.getExpiredDate());
        voucherDto.setStatus(voucher.getStatus());
        if (voucher.getUnitValue() != null) {
            voucherDto.setUnitValue(voucher.getUnitValue());
        }
        voucherDto.setVoucherType(voucher.getVoucherType());

        return voucherDto;
    }

    // Convert VoucherDto to Voucher
    public static Voucher toEntity(VoucherDto dto) {
        if (dto == null) {
            return null;
        }

        Voucher voucher = new Voucher();
        voucher.setId(dto.getId());
        voucher.setBrand_id(dto.getBrand_id());
        voucher.setVoucherCode(dto.getVoucherCode());
        voucher.setQrCode(dto.getQrCode());
        voucher.setImage(dto.getImage());
        voucher.setValue(dto.getValue());
        voucher.setDescription(dto.getDescription());
        voucher.setExpiredDate(dto.getExpiredDate());
        voucher.setStatus(dto.getStatus());
        if (dto.getUnitValue() != null) {
            voucher.setUnitValue(dto.getUnitValue());
        }
        voucher.setVoucherType(dto.getVoucherType());

        return voucher;
    }
}