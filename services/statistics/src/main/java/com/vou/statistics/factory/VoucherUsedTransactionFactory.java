package com.vou.statistics.factory;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.entity.VoucherUsedTransaction;

public class VoucherUsedTransactionFactory implements TransactionFactory {
    @Override
    public Transaction createTransaction() {
        return new VoucherUsedTransaction();
    }
}
