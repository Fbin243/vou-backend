package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_SHARED;

import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.ItemSharedTransactionRepository;
import com.vou.statistics.service.PlayerItemService;

public class ItemSharedTransactionStrategy implements TransactionStrategy {

    private PlayerItemService                   playerItemService;
    private ItemSharedTransactionRepository     itemSharedTransactionRepository;
    
    @Override
    public boolean processTransaction(Transaction transaction) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_SHARED)) {
            throw new IllegalArgumentException("Invalid transaction type for ItemSharedTransactionStrategy");
        }

        try {
            System.out.println("ItemSharedTransactionStrategy.processTransaction1");
            playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getArtifactId(), transaction.getQuantity()));
            System.out.println("ItemSharedTransactionStrategy.processTransaction2");
            playerItemService.deletePlayerItem(new PlayerItemDto(transaction.getPlayerId(), transaction.getArtifactId(), transaction.getQuantity()));
            System.out.println("ItemSharedTransactionStrategy.processTransaction3");
            saveTransaction(transaction);
            System.out.println("ItemSharedTransactionStrategy.processTransaction4");
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
