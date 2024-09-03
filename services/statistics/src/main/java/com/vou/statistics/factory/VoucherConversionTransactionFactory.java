package com.vou.statistics.factory;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.entity.VoucherConversionTransaction;

public class VoucherConversionTransactionFactory implements TransactionFactory {
    @Override
    public Transaction createTransaction() {
        return new VoucherConversionTransaction();
    }
}
