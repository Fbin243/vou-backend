package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnItemDto {
    private String id;

    private BrandDto brand;

    private String name;

    private String icon;

    private String description;

    private int numberOfItem;
}
