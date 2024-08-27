package com.vou.events.service;

import com.vou.events.dto.VoucherDto;
import com.vou.events.entity.*;
import com.vou.events.mapper.BrandMapper;
import com.vou.events.mapper.VoucherMapper;
import com.vou.events.repository.*;
import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.common.ItemId_Quantity;
import com.vou.pkg.exception.NotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VouchersService implements IVouchersService {
    
    private final VoucherRepository     voucherRepository;
    private final ItemRepository        itemRepository;
    private final VoucherItemRepository voucherItemRepository;
    private final BrandRepository       brandRepository;

    @Override
    public List<VoucherDto> fetchAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(VoucherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public VoucherDto fetchVoucherById(String id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Voucher", "id", id)
        );
        return VoucherMapper.toDto(voucher);
    }

    @Override
    public String createVoucher(VoucherDto voucherDto) {
        if (brandRepository.findById(voucherDto.getBrand().getId()) == null) {
            brandRepository.save(BrandMapper.toEntity(voucherDto.getBrand()));
        }

        Brand brand = brandRepository.findById(voucherDto.getBrand().getId())
                     .orElseGet(() -> brandRepository.save(BrandMapper.toEntity(voucherDto.getBrand())));

        Voucher voucher = VoucherMapper.toEntity(voucherDto);
        voucher.setBrand(brand);
        voucher.setId(null);
        Voucher createdVoucher = voucherRepository.save(voucher);
        return createdVoucher.getId().toString();
    }

    @Override
    public boolean updateVoucher(VoucherDto voucherDto) {
        try {
            Voucher voucher = voucherRepository.findById(voucherDto.getId()).orElseThrow(
                    () -> new NotFoundException("Voucher", "id", voucherDto.getId())
            );

            Voucher updatedVoucher = VoucherMapper.toEntity(voucherDto);
            updatedVoucher.setId(voucher.getId());
            voucherRepository.save(updatedVoucher);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteVoucher (String id) {
        try {
            Voucher voucher = voucherRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Voucher", "id", id)
            );

            voucherRepository.delete(voucher);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addVoucherItemConversion (String voucherId, List<ItemId_Quantity> itemIds_quantities) {
        try {
            Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(
                    () -> new NotFoundException("Voucher", "id", voucherId)
            );

            for (ItemId_Quantity itemIds_quantity : itemIds_quantities) {
                Item item = itemRepository.findById(itemIds_quantity.getItemId()).orElseThrow(
                        () -> new NotFoundException("Item", "id", itemIds_quantity.getItemId())
                );

                VoucherItem voucherItem = voucherItemRepository.findByVoucherAndItem(voucherId, itemIds_quantity.getItemId());
                if (voucherItem != null) {
                    voucherItem.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    voucherItem.setNumberOfItem(itemIds_quantity.getQuantity());
                    voucherItemRepository.save(voucherItem);
                }
                else {
                    voucherItem = new VoucherItem();
                    voucherItem.setVoucher(voucher);
                    voucherItem.setItem(item);
                    voucherItem.setActiveStatus(EventIntermediateTableStatus.ACTIVE);
                    voucherItem.setNumberOfItem(itemIds_quantity.getQuantity());
                    voucherItemRepository.save(voucherItem);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}