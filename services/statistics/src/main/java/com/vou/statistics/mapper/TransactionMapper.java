package com.vou.statistics.mapper;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_SHARED;
import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_CONVERSION;
import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_USED;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.vou.statistics.dto.ItemSharedTransactionDto;
import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.dto.VoucherConversionTransactionDto;
import com.vou.statistics.dto.VoucherUsedTransactionDto;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.entity.VoucherConversionTransaction;
import com.vou.statistics.entity.VoucherUsedTransaction;
import com.vou.statistics.model.Transaction;

@Service
public class TransactionMapper {
    
    // Convert entity to DTO
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction instanceof ItemSharedTransaction) {
            ItemSharedTransaction itemSharedTransaction = (ItemSharedTransaction) transaction;
            ItemSharedTransactionDto dto = new ItemSharedTransactionDto();
            // Copy common fields
            dto.setId(itemSharedTransaction.getId().toString());
            dto.setPlayerId(itemSharedTransaction.getPlayerId());
            dto.setRecipientId(itemSharedTransaction.getRecipientId());
            dto.setArtifactId(itemSharedTransaction.getArtifactId());
            dto.setTransactionDate(itemSharedTransaction.getTransactionDate());
            dto.setQuantity(itemSharedTransaction.getQuantity());
            dto.setTransactionType(TRANSACTION_TYPE_ITEM_SHARED);
            // Copy specific fields
            dto.setGameId(itemSharedTransaction.getGameId());
            return dto;
        } else if (transaction instanceof VoucherUsedTransaction) {
            VoucherUsedTransaction voucherTransaction = (VoucherUsedTransaction) transaction;
            VoucherUsedTransactionDto dto = new VoucherUsedTransactionDto();
            // Copy common fields
            dto.setId(voucherTransaction.getId().toString());
            dto.setPlayerId(voucherTransaction.getPlayerId());
            dto.setRecipientId(voucherTransaction.getRecipientId());
            dto.setArtifactId(voucherTransaction.getArtifactId());
            dto.setTransactionDate(voucherTransaction.getTransactionDate());
            dto.setQuantity(voucherTransaction.getQuantity());
            dto.setTransactionType(TRANSACTION_TYPE_VOUCHER_USED);
            // Copy specific fields
            dto.setEventId(voucherTransaction.getEventId());
            return dto;
        } else if (transaction instanceof VoucherConversionTransaction) {
            VoucherConversionTransaction voucherTransaction = (VoucherConversionTransaction) transaction;
            VoucherConversionTransactionDto dto = new VoucherConversionTransactionDto();
            // Copy common fields
            dto.setId(voucherTransaction.getId().toString());
            dto.setPlayerId(voucherTransaction.getPlayerId());
            dto.setRecipientId(voucherTransaction.getRecipientId());
            dto.setArtifactId(voucherTransaction.getArtifactId());
            dto.setTransactionDate(voucherTransaction.getTransactionDate());
            dto.setQuantity(voucherTransaction.getQuantity());
            dto.setTransactionType(TRANSACTION_TYPE_VOUCHER_CONVERSION);
            // Copy specific fields
            dto.setEventId(voucherTransaction.getEventId());
            dto.setItems(voucherTransaction.getItems());
            return dto;
        }

        return null;
    }

    // Convert DTO to entity
    public static Transaction toEntity(TransactionDto dto) {
        if (dto instanceof ItemSharedTransactionDto) {
            ItemSharedTransactionDto itemSharedTransactionDto = (ItemSharedTransactionDto) dto;
            ItemSharedTransaction entity = new ItemSharedTransaction();
            // Copy common fields
            entity.setId(new ObjectId(itemSharedTransactionDto.getId()));
            entity.setPlayerId(itemSharedTransactionDto.getPlayerId());
            entity.setRecipientId(itemSharedTransactionDto.getRecipientId());
            entity.setArtifactId(itemSharedTransactionDto.getArtifactId());
            entity.setTransactionDate(itemSharedTransactionDto.getTransactionDate());
            entity.setQuantity(itemSharedTransactionDto.getQuantity());
            // Copy specific fields
            entity.setGameId(itemSharedTransactionDto.getGameId());
            return entity;
        } else if (dto instanceof VoucherUsedTransactionDto) {
            VoucherUsedTransactionDto voucherTransactionDto = (VoucherUsedTransactionDto) dto;
            VoucherUsedTransaction entity = new VoucherUsedTransaction();
            // Copy common fields
            entity.setId(new ObjectId(voucherTransactionDto.getId()));
            entity.setPlayerId(voucherTransactionDto.getPlayerId());
            entity.setRecipientId(voucherTransactionDto.getRecipientId());
            entity.setArtifactId(voucherTransactionDto.getArtifactId());
            entity.setTransactionDate(voucherTransactionDto.getTransactionDate());
            entity.setQuantity(voucherTransactionDto.getQuantity());
            // Copy specific fields
            entity.setEventId(voucherTransactionDto.getEventId());
            return entity;
        } else if (dto instanceof VoucherConversionTransactionDto) {
            VoucherConversionTransactionDto voucherTransactionDto = (VoucherConversionTransactionDto) dto;
            VoucherConversionTransaction entity = new VoucherConversionTransaction();
            // Copy common fields
            entity.setId(new ObjectId(voucherTransactionDto.getId()));
            entity.setPlayerId(voucherTransactionDto.getPlayerId());
            entity.setRecipientId(voucherTransactionDto.getRecipientId());
            entity.setArtifactId(voucherTransactionDto.getArtifactId());
            entity.setTransactionDate(voucherTransactionDto.getTransactionDate());
            entity.setQuantity(voucherTransactionDto.getQuantity());
            // Copy specific fields
            entity.setEventId(voucherTransactionDto.getEventId());
            entity.setItems(voucherTransactionDto.getItems());
            return entity;
        }

        return null;
    }
}
