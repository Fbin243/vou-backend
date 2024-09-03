package com.vou.statistics.factory;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.entity.ItemSharedTransaction;

public class ItemSharedTransactionFactory implements TransactionFactory {
    @Override
    public Transaction createTransaction() {
        return new ItemSharedTransaction();
    }
}
