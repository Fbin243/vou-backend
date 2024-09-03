package com.vou.statistics.strategy;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

public interface TransactionStrategy {
    boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService);
    boolean saveTransaction(Transaction transaction);
}
