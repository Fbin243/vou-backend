package com.vou.statistics.service;

import com.vou.statistics.entity.ItemSharedTransaction;

import com.vou.statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemSharedTransactionService extends TransactionService<ItemSharedTransaction> {
    public ItemSharedTransactionService(TransactionRepository<ItemSharedTransaction> transactionRepository) {
        super(transactionRepository);
    }

    // private ItemSharedTransactionRepository itemSharedTransactionRepository;

    // @Override
    // public List<ItemSharedTransaction> getAllTransactions() {
    //     return itemSharedTransactionRepository.findAll();
    // }

    // @Override
    // public ItemSharedTransaction getTransactionById(ObjectId id) {
    //     return itemSharedTransactionRepository.findById(id).orElse(null);
    // }

    // @Override
    // public List<ItemSharedTransaction> getTransactionsByPlayer(String senderId) {
    //     return super.getTransactionsByPlayer(senderId);
    // }

    // @Override
    // public List<ItemSharedTransaction> getTransactionsByRecipient(String recipientId) {
    //     return super.getTransactionsByRecipient(recipientId);
    // }

    // @Override
    // public List<ItemSharedTransaction> getTransactionsBySenderAndRecipient(String senderId, String recipientId) {
    //     return super.getTransactionsBySenderAndRecipient(senderId, recipientId);
    // }

    // @Override
    // public List<ItemSharedTransaction> getTransactionsByArtifact(String artifactId) {
    //     return super.getTransactionsByArtifact(artifactId);
    // }

    // @Override
    // public boolean processItemTransaction(ItemSharedTransaction transaction) {
    //     try {
    //         playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getItemId(), transaction.getQuantity()));
    //         playerItemService.deletePlayerItem(new PlayerItemDto(transaction.getSenderId(), transaction.getItemId(), transaction.getQuantity()));
    //         saveItemTransaction(transaction);
    //     }
    //     catch (Exception e) {
    //         e.getStackTrace();
    //         return false;
    //     }
    //     return true;
    // }

    // @Override
    // public void saveItemTransaction(ItemSharedTransaction transaction) {
    //     itemSharedTransactionRepository.save(transaction);
    // }    
}
