package com.vou.statistics.factory;

import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.model.Transaction;

public interface TransactionFactory {
    Transaction createTransaction(TransactionDto transactionDto);
}
