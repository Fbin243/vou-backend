package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.VoucherConversionTransaction;

@Repository
public interface VoucherConversionTransactionRepository extends TransactionRepository<VoucherConversionTransaction>, VoucherConversionTransactionRepositoryCustom {
    Optional<VoucherConversionTransaction> findTransactionByPlayerId(String playerId);
    List<VoucherConversionTransaction> findTransactionsByPlayerId(String playerId);
    List<VoucherConversionTransaction> findTransactionsByArtifactId(String artifactId);
}
