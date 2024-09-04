package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import com.vou.statistics.entity.ItemReceivedTransaction;

public interface ItemReceivedTransactionRepositoryCustom {
    // Optional<ItemReceivedTransaction> findTransactionByPlayerId(String playerId);
    // Optional<ItemReceivedTransaction> findTransactionByPlayerIdAndRecipientId(String playerId, String recipientId);
    // List<ItemReceivedTransaction> findTransactionsByPlayerId(String playerId);
    List<ItemReceivedTransaction> findTransactionsByRecipientId(String recipientId);
    // List<ItemReceivedTransaction> findTransactionsByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<ItemReceivedTransaction> findTransactionsByArtifactId(String artifactId);
}
