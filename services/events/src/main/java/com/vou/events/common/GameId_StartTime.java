package com.vou.events.common;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameId_StartTime {
    Long gameId;
    String gameType;
    LocalTime startTime;
}