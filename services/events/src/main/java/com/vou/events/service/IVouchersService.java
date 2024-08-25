package com.vou.events.service;

import com.vou.events.common.ItemId_Quantity;
import com.vou.events.dto.VoucherDto;

import java.util.List;

/**
 * Service interface for managing events.
 */
public interface IVouchersService {

    /**
     * Fetches all vouchers.
     *
     * @return a list of all vouchers
     */
    default List<VoucherDto> fetchAllVouchers() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all vouchers that are currently in progress.
     *
     * @return a list of all vouchers in progress
     */
    default List<VoucherDto> fetchVouchersInProgress() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches a voucher by its id.
     *  
     * @param voucherId the id of the voucher to fetch
     * @return the fetched voucher
     */
    default VoucherDto fetchVoucherById(String voucherId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all vouchers by brand.
     *  
     * @param brandId the id of the brand to fetch vouchers for
     * @return a list of vouchers for the specified brand
     */
    default List<VoucherDto> fetchVouchersByBrand(String brandId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Fetches all vouchers by brands.
     *  
     * @param brandIds the ids of the brands to fetch vouchers for
     * @return a list of vouchers for the specified brands
     */
    default List<VoucherDto> fetchVouchersByBrands(List<String> brandIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new voucher.
     *  
     * @param voucherDto the voucher to create
     * @return the created voucher
     */
    default String createVoucher(VoucherDto voucherDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Updates an existing voucher.
     *  
     * @param voucherDto the voucher to update
     * @return the updated voucher
     */
    default boolean updateVoucher(VoucherDto voucherDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Deletes an existing voucher.
     *  
     * @param voucherId the id of the voucher to delete
     */
    default boolean deleteVoucher(String voucherId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * add items conversion to voucher
     * 
     * @param voucherId the id of the voucher to add items
     * @param itemIds the ids of the items to add
     * @return true if the items were added successfully, false otherwise
     */
    default boolean addVoucherItemConversion(String voucherId, List<ItemId_Quantity> itemIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}