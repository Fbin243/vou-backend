package com.vou.statistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemSharedTransactionDto extends TransactionDto {
    private Long gameId;
}
