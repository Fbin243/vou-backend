package com.vou.events.dto;

import java.time.LocalTime;
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
public class ReturnGameDto {
    private Long id;

    private String name;

    private String image;

    private String type;

    private boolean itemSwappable;

    private String instruction;

    private LocalTime startTime;
}
