package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.ItemSharedTransaction;

@Repository
public interface ItemSharedTransactionRepository extends TransactionRepository<ItemSharedTransaction>, ItemSharedTransactionRepositoryCustom {
    Optional<ItemSharedTransaction> findTransactionByPlayerId(String playerId);
    Optional<ItemSharedTransaction> findTransactionByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemSharedTransaction> findTransactionsByPlayerId(String playerId);
    List<ItemSharedTransaction> findTransactionsByRecipientId(String recipientId);
    List<ItemSharedTransaction> findTransactionsByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemSharedTransaction> findTransactionsByArtifactId(String artifactId);
}