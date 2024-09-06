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
@JsonTypeName("voucher_used")
public class VoucherUsedTransactionData extends TransactionData {
    private String eventId;

    // Full constructor, calling parent constructor
    public VoucherUsedTransactionData(String playerId, String recipientId, String artifactId, LocalDateTime transactionDate, int quantity, String transactionType, String eventId) {
        super(playerId, recipientId, artifactId, transactionDate, quantity, transactionType); // Calls the TransactionData constructor
        this.eventId = eventId;
    }
}
