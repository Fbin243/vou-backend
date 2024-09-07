package com.vou.statistics.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.ItemReceivedTransaction;

@Repository
public interface ItemReceivedTransactionRepository extends TransactionRepository<ItemReceivedTransaction>, ItemReceivedTransactionRepositoryCustom {
    // Optional<ItemReceivedTransaction> findTransactionByPlayerId(String playerId);
    // Optional<ItemReceivedTransaction> findTransactionByPlayerIdAndRecipientId(String playerId, String recipientId);
    // List<ItemReceivedTransaction> findTransactionsByPlayerId(String playerId);
    List<ItemReceivedTransaction> findTransactionsByRecipientId(String recipientId);
    // List<ItemReceivedTransaction> findTransactionsByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemReceivedTransaction> findTransactionsByArtifactId(String artifactId);
}