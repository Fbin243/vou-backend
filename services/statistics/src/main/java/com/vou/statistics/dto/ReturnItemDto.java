package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnItemDto {
    private ItemDto item;

    private int numberOfItem;
}