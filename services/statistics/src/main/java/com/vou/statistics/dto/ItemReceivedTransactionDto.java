package com.vou.statistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonTypeName("item_received")
public class ItemReceivedTransactionDto extends TransactionDto {
    private Long gameId;
}
