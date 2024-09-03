package com.vou.events.service;

import java.util.Map;

/**
 * Service interface for managing voucheritem.
 */
public interface IVoucherItemService {
    
    default Map<String, Integer> getItemsQuantitiesByVoucher(String voucherId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
