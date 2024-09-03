package com.vou.statistics.service;

import com.vou.statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import com.vou.statistics.entity.ItemSharedTransaction;

@Service
public class VoucherUsedTransactionService extends TransactionService<ItemSharedTransaction> {

    public VoucherUsedTransactionService(TransactionRepository<ItemSharedTransaction> transactionRepository) {
        super(transactionRepository);
    }
}
