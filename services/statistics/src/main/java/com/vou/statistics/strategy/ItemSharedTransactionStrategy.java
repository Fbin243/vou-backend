package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_SHARED;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.ItemSharedTransactionRepository;
import com.vou.statistics.repository.TransactionRepository;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
// @AllArgsConstructor
public class ItemSharedTransactionStrategy implements TransactionStrategy {

    private PlayerItemService                   playerItemService;
    private ItemSharedTransactionRepository     itemSharedTransactionRepository;


    @Autowired
    public ItemSharedTransactionStrategy(PlayerItemService playerItemService, 
                                         ItemSharedTransactionRepository itemSharedTransactionRepository) {
        this.playerItemService = playerItemService;
        this.itemSharedTransactionRepository = itemSharedTransactionRepository;
    }

    @Override
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient eventsServiceClient, TransactionRepository<Transaction> transactionRepository) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_SHARED)) {
            throw new IllegalArgumentException("Invalid transaction type for ItemSharedTransactionStrategy");
        }

        try {
            Integer numberOfItemLeftOfTheSender = playerItemService.getQuantityByPlayerIdAndItemId(transaction.getPlayerId(), transaction.getArtifactId());

            if (numberOfItemLeftOfTheSender < transaction.getQuantity()) {
                return false;
            }

            // get item by id
            ItemDto currentItem = eventsServiceClient.getItemsByIds(Collections.singletonList(transaction.getArtifactId())).get(0);

            if (currentItem == null) {
                System.out.println("Item not found in database!");
                return false;
            }

            playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getArtifactId(), currentItem.getBrand_id(), currentItem.getName(), transaction.getQuantity()));
            playerItemService.addPlayerItem(new PlayerItemDto(transaction.getPlayerId(), transaction.getArtifactId(), currentItem.getBrand_id(), currentItem.getName(), transaction.getQuantity() * -1));
            saveTransaction(transaction, transactionRepository);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean saveTransaction(Transaction transaction, TransactionRepository<Transaction> transactionRepository) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_SHARED)) {
            throw new IllegalArgumentException("Invalid transaction type for ItemSharedTransactionStrategy");
        }

        try {
            ItemSharedTransaction itemSharedTransaction = (ItemSharedTransaction) transaction;
            transactionRepository.save(itemSharedTransaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
}
