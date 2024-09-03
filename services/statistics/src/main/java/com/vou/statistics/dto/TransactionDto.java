package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.Setter;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Data
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "transactionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ItemSharedTransactionDto.class, name = "item_shared"),
    @JsonSubTypes.Type(value = VoucherUsedTransactionDto.class, name = "voucher_used"),
    @JsonSubTypes.Type(value = VoucherConversionTransactionDto.class, name = "voucher_conversion")
})
public abstract class TransactionDto {
    // @Id
    private String id;
    private String playerId;
    private String recipientId;
    private String artifactId;
    private LocalDateTime transactionDate;
    private int quantity;
    private String transactionType;

    public TransactionDto(String playerId, String recipientId, String artifactId, LocalDateTime transactionDate, int quantity, String transactionType) {
        this.playerId = playerId;
        this.recipientId = recipientId;
        this.artifactId = artifactId;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }
}
