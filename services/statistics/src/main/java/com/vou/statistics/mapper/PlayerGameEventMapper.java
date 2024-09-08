package com.vou.statistics.mapper;

import org.springframework.stereotype.Service;

import com.vou.statistics.dto.PlayerGameEventDto;
import com.vou.statistics.entity.PlayerGameEvent;

@Service
public class PlayerGameEventMapper {
    
    // Convert PlayerGame to PlayerGameDto
    public static PlayerGameEventDto toDto(PlayerGameEvent playerGame) {
        if (playerGame == null) {
            return null;
        }

        PlayerGameEventDto playerGameDto = new PlayerGameEventDto();
        playerGameDto.setPlayerId(playerGame.getPlayerId());
        playerGameDto.setGameId(playerGame.getGameId());
        playerGameDto.setEventId(playerGame.getEventId());

        return playerGameDto;
    }

    // Convert PlayerGameDto to PlayerGame
    public static PlayerGameEvent toEntity(PlayerGameEventDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerGameEvent playerGame = new PlayerGameEvent();
        playerGame.setPlayerId(dto.getPlayerId());
        playerGame.setGameId(dto.getGameId());
        playerGame.setEventId(dto.getEventId());

        return playerGame;
    }
}
