package com.vou.statistics.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerGameEventDto {
    private String playerId;

    private String gameId;

    private String eventId;
}
