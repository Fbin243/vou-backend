package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import com.vou.statistics.entity.ItemSharedTransaction;

public interface ItemSharedTransactionRepositoryCustom {
    Optional<ItemSharedTransaction> findTransactionByPlayerId(String playerId);
    Optional<ItemSharedTransaction> findTransactionByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemSharedTransaction> findTransactionsByPlayerId(String playerId);
    List<ItemSharedTransaction> findTransactionsByRecipientId(String recipientId);
    List<ItemSharedTransaction> findTransactionsByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemSharedTransaction> findTransactionsByArtifactId(String artifactId);
}
