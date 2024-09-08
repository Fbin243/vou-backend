package com.vou.events.repository;

import com.vou.events.entity.VoucherItem;
import com.vou.events.model.VoucherItemId;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherItemRepository extends JpaRepository<VoucherItem, VoucherItemId>, VoucherItemRepositoryCustom {
    VoucherItem findByVoucherAndItem(String voucherId, String itemId);
    Map<String, Integer> getItemsQuantitiesByVoucher(String voucherId);
    List<VoucherItem> findByVoucher(String voucherId);
}