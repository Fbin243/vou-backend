package com.vou.statistics.strategy;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_USED;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.dto.ReturnVoucherDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.VoucherUsedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.vou.statistics.repository.TransactionRepository;
import com.vou.statistics.repository.VoucherUsedTransactionRepository;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class VoucherUsedTransactionStrategy implements TransactionStrategy {

    private PlayerVoucherService                playerVoucherService;
    private VoucherUsedTransactionRepository    voucherUsedTransactionRepository;

    @Override
    public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient eventsServiceClient, TransactionRepository<Transaction> transactionRepository) {
        if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_VOUCHER_USED)) {
            throw new IllegalArgumentException("Invalid transaction type for VoucherUsedTransactionStrategy");
        }

        try {
            VoucherUsedTransaction voucherUsedTransaction = (VoucherUsedTransaction) transaction;

            int existVoucherQuantity = playerVoucherService.getQuantityByPlayerIdAndVoucherId(voucherUsedTransaction.getPlayerId(), voucherUsedTransaction.getArtifactId());

            if (existVoucherQuantity < voucherUsedTransaction.getQuantity()) {
                return false;
            }

            // get Voucher By Id
            VoucherDto currentVoucher = eventsServiceClient.getVouchersByIds(Collections.singletonList(voucherUsedTransaction.getArtifactId())).get(0);

            if (currentVoucher == null) {
                return false;
            }

            if (currentVoucher.getVoucherType().equalsIgnoreCase("online")) {
                List<ReturnVoucherDto> vouchers = eventsServiceClient.getVouchersByEvent(voucherUsedTransaction.getEventId());
                Random rand = new Random();
                for (int i = 0; i < voucherUsedTransaction.getQuantity(); ++i) {
                    int index = rand.nextInt(vouchers.size());
                    playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherUsedTransaction.getPlayerId(), voucherUsedTransaction.getArtifactId(), currentVoucher.getBrand_id(), currentVoucher.getVoucherCode(), -1));
                    playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherUsedTransaction.getPlayerId(), vouchers.get(index).getId(), vouchers.get(index).getBrand_id(), currentVoucher.getVoucherCode(), 1));
                }
                // playerItemService.addItemToPlayer(voucherUsedTransaction.getPlayerId(), voucherDto.getArtifactId(), voucherUsedTransaction.getQuantity());
            } else {
                playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherUsedTransaction.getPlayerId(), voucherUsedTransaction.getArtifactId(), currentVoucher.getBrand_id(), currentVoucher.getVoucherCode(), voucherUsedTransaction.getQuantity() * -1));
            }
            
            // update player's voucher
            // playerVoucherService.addPlayerVoucher(new PlayerVoucherDto(voucherUsedTransaction.getPlayerId(), voucherUsedTransaction.getArtifactId(), voucherUsedTransaction.getQuantity() * -1));

            // save voucher used transaction
            saveTransaction(voucherUsedTransaction, transactionRepository);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean saveTransaction(Transaction transaction, TransactionRepository<Transaction> transactionRepository) {
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
