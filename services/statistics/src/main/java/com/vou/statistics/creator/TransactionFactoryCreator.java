package com.vou.statistics.creator;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_SHARED;
import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_RECEIVED;
import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_CONVERSION;
import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_USED;

import com.vou.statistics.factory.TransactionFactory;
import com.vou.statistics.factory.VoucherConversionTransactionFactory;
import com.vou.statistics.factory.VoucherUsedTransactionFactory;
import com.vou.statistics.strategy.ItemSharedTransactionStrategy;
import com.vou.statistics.strategy.ItemReceivedTransactionStrategy;
import com.vou.statistics.strategy.TransactionStrategy;
import com.vou.statistics.strategy.VoucherConversionTransactionStrategy;
import com.vou.statistics.strategy.VoucherUsedTransactionStrategy;
import com.vou.statistics.factory.ItemSharedTransactionFactory;
import com.vou.statistics.factory.ItemReceivedTransactionFactory;

public class TransactionFactoryCreator {
    public static TransactionFactory getTransactionFactory(String transactionType) {
        switch (transactionType.toLowerCase()) {
            case TRANSACTION_TYPE_ITEM_SHARED:
                return new ItemSharedTransactionFactory();
            case TRANSACTION_TYPE_ITEM_RECEIVED:
                return new ItemReceivedTransactionFactory();
            case TRANSACTION_TYPE_VOUCHER_CONVERSION:
                return new VoucherConversionTransactionFactory();
            case TRANSACTION_TYPE_VOUCHER_USED:
                return new VoucherUsedTransactionFactory();
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }
    }

    public static TransactionStrategy getTransactionStrategy(String transactionType) {
        switch (transactionType.toLowerCase()) {
            case TRANSACTION_TYPE_ITEM_SHARED:
                return new ItemSharedTransactionStrategy();
            case TRANSACTION_TYPE_ITEM_RECEIVED:
                return new ItemReceivedTransactionStrategy();
            case TRANSACTION_TYPE_VOUCHER_CONVERSION:
                return new VoucherConversionTransactionStrategy();
            case TRANSACTION_TYPE_VOUCHER_USED:
                return new VoucherUsedTransactionStrategy();
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }
    }
}

