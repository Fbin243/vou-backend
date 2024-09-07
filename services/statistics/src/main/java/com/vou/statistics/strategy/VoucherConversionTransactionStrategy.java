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
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class VoucherConversionTransactionStrategy implements TransactionStrategy {
    
    private EventsServiceClient                         eventsServiceClient;
    private PlayerVoucherService                        playerVoucherService;
    private PlayerItemService                           playerItemService;
    private VoucherConversionTransactionRepository      voucherConversionTransactionRepository;

    private static final Logger logger = Logger.getLogger(VoucherConversionTransactionStrategy.class.getName());

    @Override
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient _eventsServiceClient) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_CONVERSION)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherConversionTransactionStrategy");
        }

        try {
            VoucherConversionTransaction voucherItemsConversionTransaction = (VoucherConversionTransaction) transaction;
            logger.info("Processing VoucherConversionTransaction: " + voucherItemsConversionTransaction.toString());
            Map<String, Integer> items_quantities = _eventsServiceClient.getItemsQuantitiesByVoucher(voucherItemsConversionTransaction.getArtifactId());
            
            // check

            logger.info("Items quantities: " + items_quantities.toString());
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                if (items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() > item_quantity.getQuantity()) {
                    return false;
                }
            }
            logger.info("Items quantities are enough for conversion");

            // enough conditions to trade items for voucher
            playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherItemsConversionTransaction.getPlayerId(), voucherItemsConversionTransaction.getArtifactId(), voucherItemsConversionTransaction.getQuantity()));
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                playerItemService.addPlayerItem(new PlayerItemDto(voucherItemsConversionTransaction.getPlayerId(), item_quantity.getItemId(), items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() * -1));
            }

            logger.info("VoucherConversionTransaction processed successfully");

            // save voucher transactions
            saveTransaction(voucherItemsConversionTransaction);

            // update events_vouchers table
            eventsServiceClient.addQuantityToEventVoucher(new EventVoucherAndAdditionQuantityDto(voucherItemsConversionTransaction.getEventId(), voucherItemsConversionTransaction.getArtifactId(), voucherItemsConversionTransaction.getQuantity() * -1));

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
