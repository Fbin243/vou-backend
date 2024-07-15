package com.vou.games.dto;

import lombok.Data;

@Data
public class GameDto {
    private Long id;

    private String name;

    private String image;

    private String type;

    private boolean itemSwappable;

    private String instruction;
}
