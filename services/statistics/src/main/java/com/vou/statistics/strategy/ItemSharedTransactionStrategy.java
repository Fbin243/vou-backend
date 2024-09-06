package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_SHARED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.ItemSharedTransactionRepository;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient eventsServiceClient) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_SHARED)) {
            throw new IllegalArgumentException("Invalid transaction type for ItemSharedTransactionStrategy");
        }

        try {
            playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getArtifactId(), transaction.getQuantity()));
            playerItemService.deletePlayerItem(new PlayerItemDto(transaction.getPlayerId(), transaction.getArtifactId(), transaction.getQuantity()));
            saveTransaction(transaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean saveTransaction(Transaction transaction) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_SHARED)) {
            throw new IllegalArgumentException("Invalid transaction type for ItemSharedTransactionStrategy");
        }

        try {
            ItemSharedTransaction itemSharedTransaction = (ItemSharedTransaction) transaction;
            itemSharedTransactionRepository.save(itemSharedTransaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
}
