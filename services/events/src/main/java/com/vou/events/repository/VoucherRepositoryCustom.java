package com.vou.events.repository;

import java.util.List;

import com.vou.events.entity.Voucher;

public interface VoucherRepositoryCustom {
    List<Voucher> findByBrand(String brandId);
    List<Voucher> findByBrands(List<String> brandIds);
    List<Voucher> findByIds(List<String> ids);
}
