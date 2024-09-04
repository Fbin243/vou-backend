package com.vou.statistics.context;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import com.vou.statistics.strategy.TransactionStrategy;

public class TransactionContext {
    private TransactionStrategy transactionStrategy;

    public void setTransactionStrategy(TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    public boolean executeStrategy(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient eventsServiceClient) {
        return transactionStrategy.processTransaction(transaction, playerVoucherService, playerItemService, eventsServiceClient);
    }
}
