package com.vou.statistics.context;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.strategy.TransactionStrategy;

public class TransactionContext {
    private TransactionStrategy transactionStrategy;

    public void setTransactionStrategy(TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    public boolean executeStrategy(Transaction transaction) {
        return transactionStrategy.processTransaction(transaction);
    }
}
