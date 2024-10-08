package com.vou.events.service;

import com.vou.events.dto.BrandDto;
import com.vou.events.dto.VoucherDto;
import com.vou.events.entity.*;
import com.vou.events.mapper.VoucherMapper;
import com.vou.events.repository.*;
import com.vou.events.client.UsersServiceClient;
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
    private final UsersServiceClient    usersServiceClient;

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
    public List<VoucherDto> fetchVouchersByIds(List<String> ids) {
        List<Voucher> vouchers = voucherRepository.findByIds(ids);
        return vouchers.stream().map(VoucherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDto> fetchVouchersByBrand(String brandId) {
        List<Voucher> vouchers = voucherRepository.findByBrand(brandId);
        return vouchers.stream().map(VoucherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDto> fetchVouchersByBrands(List<String> brandIds) {
        List<Voucher> vouchers = voucherRepository.findByBrands(brandIds);
        return vouchers.stream().map(VoucherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public VoucherDto createVoucher(VoucherDto voucherDto) {
        
        BrandDto brandDto = usersServiceClient.getBrand(voucherDto.getBrand_id());

        Voucher voucher = VoucherMapper.toEntity(voucherDto);
        voucher.setBrand_id(brandDto.getId());
        voucherRepository.save(voucher);
        return VoucherMapper.toDto(voucher);
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

            if (!this.updateActiveStatusForVouchersConversion(voucherId, EventIntermediateTableStatus.INACTIVE)) {
                System.out.println("Can't update the active status of VoucherItemConversion table!");
                return false;
            }

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
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean updateActiveStatusForVouchersConversion(String voucherId, EventIntermediateTableStatus activeStatus) {
        try {
            List<VoucherItem> voucherItems = voucherItemRepository.findByVoucher(voucherId);
            for (VoucherItem voucherItem : voucherItems) {
                voucherItem.setActiveStatus(activeStatus);
                voucherItemRepository.save(voucherItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}