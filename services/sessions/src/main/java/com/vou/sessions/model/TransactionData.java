package com.vou.sessions.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.Setter;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "transactionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ItemSharedTransactionData.class, name = "item_shared"),
    @JsonSubTypes.Type(value = VoucherUsedTransactionData.class, name = "voucher_used"),
    @JsonSubTypes.Type(value = VoucherConversionTransactionData.class, name = "voucher_conversion"),
    @JsonSubTypes.Type(value = ItemReceivedTransactionData.class, name = "item_received")
})
@JsonSerialize
public abstract class TransactionData {
    // @Id
    private String id;
    private String playerId;
    private String recipientId;
    private String artifactId;
    private LocalDateTime transactionDate;
    private int quantity;
    private String transactionType;

    public TransactionData(String playerId, String recipientId, String artifactId, LocalDateTime transactionDate, int quantity, String transactionType) {
        this.playerId = playerId;
        this.recipientId = recipientId;
        this.artifactId = artifactId;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }
}
