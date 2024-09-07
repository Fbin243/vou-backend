package com.vou.statistics.factory;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.mapper.TransactionMapper;

public class VoucherUsedTransactionFactory implements TransactionFactory {
    @Override
    public Transaction createTransaction(TransactionDto transactionDto) {
        return TransactionMapper.toEntity(transactionDto);
    }
}
