package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import com.vou.statistics.entity.VoucherUsedTransaction;

public interface VoucherUsedTransactionRepositoryCustom {
    Optional<VoucherUsedTransaction> findTransactionByPlayerId(String playerId);
    List<VoucherUsedTransaction> findTransactionsByPlayerId(String playerId);
    List<VoucherUsedTransaction> findTransactionsByArtifactId(String artifactId);
}