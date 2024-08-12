package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.vou.events.common.GameId_StartTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddGamesRequestDto {
    private String eventId;
    private List<GameId_StartTime> gameId_StartTimes;
}