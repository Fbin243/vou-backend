package com.vou.sessions.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;

@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
@JsonTypeName("item_received")
public class ItemReceivedTransactionData extends TransactionData {
    private Long gameId;

    // Full constructor, calling parent constructor
    public ItemReceivedTransactionData(String playerId, String recipientId, String artifactId, LocalDateTime transactionDate, int quantity, String transactionType, Long gameId) {
        super(playerId, recipientId, artifactId, transactionDate, quantity, transactionType); // Calls the TransactionData constructor
        this.gameId = gameId;
    }
}
