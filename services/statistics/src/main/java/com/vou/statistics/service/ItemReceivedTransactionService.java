package com.vou.statistics.service;

import com.vou.statistics.entity.ItemReceivedTransaction;

import com.vou.statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemReceivedTransactionService extends TransactionService<ItemReceivedTransaction> {
    public ItemReceivedTransactionService(TransactionRepository<ItemReceivedTransaction> transactionRepository) {
        super(transactionRepository);
    }  
}
