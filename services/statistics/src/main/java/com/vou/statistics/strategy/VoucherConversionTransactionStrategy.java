package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_CONVERSION;

import java.util.Map;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.EventVoucherAndAdditionQuantityDto;
import com.vou.statistics.dto.ItemId_Quantity;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.entity.VoucherConversionTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import com.vou.statistics.repository.VoucherConversionTransactionRepository;

public class VoucherConversionTransactionStrategy implements TransactionStrategy {
    
    private EventsServiceClient                         eventsServiceClient;
    private PlayerVoucherService                        playerVoucherService;
    private PlayerItemService                           playerItemService;
    private VoucherConversionTransactionRepository      voucherConversionTransactionRepository;

    @Override
    public boolean processTransaction(Transaction transaction) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_CONVERSION)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherConversionTransactionStrategy");
        }

        try {
            VoucherConversionTransaction voucherItemsConversionTransaction = (VoucherConversionTransaction) transaction;
            Map<String, Integer> items_quantities = eventsServiceClient.getItemsQuantitiesByVoucher(voucherItemsConversionTransaction.getArtifactId());
            
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                if (items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() > item_quantity.getQuantity()) {
                    return false;
                }
            }

            // enough conditions to trade items for voucher
            playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherItemsConversionTransaction.getPlayerId(), voucherItemsConversionTransaction.getVoucherId(), voucherItemsConversionTransaction.getQuantity()));
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                playerItemService.addPlayerItem(new PlayerItemDto(voucherItemsConversionTransaction.getPlayerId(), item_quantity.getItemId(), items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() * -1));
            }

            // save voucher transactions
            saveTransaction(voucherItemsConversionTransaction);

            // update events_vouchers table
            eventsServiceClient.addQuantityToEventVoucher(new EventVoucherAndAdditionQuantityDto(voucherItemsConversionTransaction.getEventId(), voucherItemsConversionTransaction.getVoucherId(), voucherItemsConversionTransaction.getQuantity() * -1));

        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
    
    @Override
    public boolean saveTransaction(Transaction transaction) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_CONVERSION)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherConversionTransactionStrategy");
        }

        try {
            VoucherConversionTransaction voucherConversionTransaction = (VoucherConversionTransaction) transaction;
            voucherConversionTransactionRepository.save(voucherConversionTransaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
}
