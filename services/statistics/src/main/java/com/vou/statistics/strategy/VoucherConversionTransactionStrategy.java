package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_CONVERSION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.EventVoucherAndAdditionQuantityDto;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.ItemId_Quantity;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.VoucherConversionTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import com.vou.statistics.repository.TransactionRepository;
import com.vou.statistics.repository.VoucherConversionTransactionRepository;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class VoucherConversionTransactionStrategy implements TransactionStrategy {
    
    private EventsServiceClient                         eventsServiceClient;
    private PlayerVoucherService                        playerVoucherService;
    private PlayerItemService                           playerItemService;
    private VoucherConversionTransactionRepository      voucherConversionTransactionRepository;

    private static final Logger logger = Logger.getLogger(VoucherConversionTransactionStrategy.class.getName());

    @Override
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient _eventsServiceClient, TransactionRepository<Transaction> transactionRepository) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_CONVERSION)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherConversionTransactionStrategy");
        }

        try {
            VoucherConversionTransaction voucherItemsConversionTransaction = (VoucherConversionTransaction) transaction;
            logger.info("Processing VoucherConversionTransaction: " + voucherItemsConversionTransaction.toString());

            // get Voucher By Id
            VoucherDto currentVoucher = _eventsServiceClient.getVouchersByIds(Collections.singletonList(voucherItemsConversionTransaction.getArtifactId())).get(0);

            if (currentVoucher == null) {
                System.out.println("Voucher not found in database!");
                return false;
            }

            Map<String, Integer> items_quantities = _eventsServiceClient.getItemsQuantitiesByVoucher(voucherItemsConversionTransaction.getArtifactId());
            
            int voucherLeftOfTheEvent = _eventsServiceClient.getEventVoucherQuantity(voucherItemsConversionTransaction.getEventId(), voucherItemsConversionTransaction.getArtifactId());

            if (voucherLeftOfTheEvent < voucherItemsConversionTransaction.getQuantity()) {
                return false;
            }

            logger.info("Items quantities: " + items_quantities.toString());
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                if (items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() > item_quantity.getQuantity()) {
                    return false;
                }
            }
            logger.info("Items quantities are enough for conversion");

            // enough conditions to trade items for voucher
            playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherItemsConversionTransaction.getPlayerId(), voucherItemsConversionTransaction.getArtifactId(), currentVoucher.getBrand_id(), currentVoucher.getVoucherCode(), voucherItemsConversionTransaction.getQuantity()));
            
            List<String> itemIds = new ArrayList<>();

            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                itemIds.add(item_quantity.getItemId());
            }

            List<ItemDto> currentItems = _eventsServiceClient.getItemsByIds(itemIds);
            int _count = 0;
            
            for (ItemId_Quantity item_quantity : voucherItemsConversionTransaction.getItems()) {
                // ItemDto currentItem = eventsServiceClient.getItemsByIds(Collections.singletonList(item_quantity.getItemId())).get(0);
                playerItemService.addPlayerItem(new PlayerItemDto(voucherItemsConversionTransaction.getPlayerId(), item_quantity.getItemId(), currentVoucher.getBrand_id(), currentItems.get(_count).getName(), 2L, items_quantities.get(item_quantity.getItemId()) * voucherItemsConversionTransaction.getQuantity() * -1));
                _count++;
            }

            logger.info("VoucherConversionTransaction processed successfully");

            // save voucher transactions
            saveTransaction(voucherItemsConversionTransaction, transactionRepository);

            // update events_vouchers table
            if (_eventsServiceClient.addQuantityToEventVoucher(new EventVoucherAndAdditionQuantityDto(voucherItemsConversionTransaction.getEventId(), voucherItemsConversionTransaction.getArtifactId(), voucherItemsConversionTransaction.getQuantity() * -1))) {
                logger.info("EventVoucher updated successfully");
            }
            else {
                logger.info("EventVoucher update failed");
                return false;
            }

        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
    
    @Override
    public boolean saveTransaction(Transaction transaction, TransactionRepository<Transaction> transactionRepository) {
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
