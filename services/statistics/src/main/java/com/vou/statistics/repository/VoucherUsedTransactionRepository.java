package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.VoucherUsedTransaction;

@Repository
public interface VoucherUsedTransactionRepository extends TransactionRepository<VoucherUsedTransaction>, VoucherUsedTransactionRepositoryCustom {
    Optional<VoucherUsedTransaction> findTransactionByPlayerId(String playerId);
    List<VoucherUsedTransaction> findTransactionsByPlayerId(String playerId);
    List<VoucherUsedTransaction> findTransactionsByArtifactId(String artifactId);
}
