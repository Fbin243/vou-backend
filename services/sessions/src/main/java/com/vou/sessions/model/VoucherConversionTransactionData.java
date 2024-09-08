package com.vou.sessions.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
@JsonTypeName("voucher_conversion")
public class VoucherConversionTransactionData extends TransactionData {
    private String eventId;
    private List<ItemId_Quantity> items;
    
    // Full constructor, calling parent constructor
    public VoucherConversionTransactionData(String playerId, String recipientId, String artifactId, LocalDateTime transactionDate, int quantity, String transactionType, String eventId, List<ItemId_Quantity> items) {
        super(playerId, recipientId, artifactId, transactionDate, quantity, transactionType); // Calls the TransactionData constructor
        this.eventId = eventId;
        this.items = items;
    }
}
