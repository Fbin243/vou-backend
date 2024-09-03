package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_USED;

import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.entity.VoucherUsedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

import com.vou.statistics.repository.VoucherUsedTransactionRepository;

public class VoucherUsedTransactionStrategy implements TransactionStrategy {

    private PlayerVoucherService                playerVoucherService;
    private VoucherUsedTransactionRepository    voucherUsedTransactionRepository;

    @Override
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_USED)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherUsedTransactionStrategy");
        }

        try {
            VoucherUsedTransaction voucherUsedTransaction = (VoucherUsedTransaction) transaction;

            // update player's voucher
            playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherUsedTransaction.getPlayerId(), voucherUsedTransaction.getArtifactId(), voucherUsedTransaction.getQuantity() * -1));

            // save voucher used transaction
            saveTransaction(voucherUsedTransaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean saveTransaction(Transaction transaction) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_USED)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherUsedTransactionStrategy");
        }

        try {
            VoucherUsedTransaction voucherUsedTransaction = (VoucherUsedTransaction) transaction;
            voucherUsedTransactionRepository.save(voucherUsedTransaction);
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }

        return true;
    }
}
