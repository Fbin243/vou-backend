package com.vou.statistics.service;

import com.vou.statistics.repository.ItemReceivedTransactionRepository;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemReceivedTransaction;

import java.util.List;

import com.vou.statistics.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
public class ItemReceivedTransactionService extends TransactionService<ItemReceivedTransaction> {
    public ItemReceivedTransactionService(TransactionRepository<ItemReceivedTransaction> transactionRepository) {
        super(transactionRepository);
    }  
}
