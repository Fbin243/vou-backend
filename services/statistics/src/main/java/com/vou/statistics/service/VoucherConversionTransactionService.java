package com.vou.statistics.service;

import com.vou.statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import com.vou.statistics.entity.ItemSharedTransaction;

@Service
public class VoucherConversionTransactionService extends TransactionService<ItemSharedTransaction> {

    public VoucherConversionTransactionService(TransactionRepository<ItemSharedTransaction> transactionRepository) {
        super(transactionRepository);
    }
}
