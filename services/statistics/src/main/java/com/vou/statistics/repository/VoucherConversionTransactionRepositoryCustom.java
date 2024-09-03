package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import com.vou.statistics.entity.VoucherConversionTransaction;

public interface VoucherConversionTransactionRepositoryCustom {
    Optional<VoucherConversionTransaction> findTransactionByPlayerId(String playerId);
    List<VoucherConversionTransaction> findTransactionsByPlayerId(String playerId);
    List<VoucherConversionTransaction> findTransactionsByArtifactId(String artifactId);
}
