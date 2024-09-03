package com.vou.statistics.strategy;

import com.vou.statistics.model.Transaction;

public interface TransactionStrategy {
    boolean processTransaction(Transaction transaction);
    boolean saveTransaction(Transaction transaction);
}
