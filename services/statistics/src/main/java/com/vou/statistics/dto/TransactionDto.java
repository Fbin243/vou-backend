package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class TransactionDto {
    private String id;
    private String playerId;
    private String recipientId;
    private String artifactId;
    private LocalDateTime transactionDate;
    private int quantity;
    private String transactionType;
}
