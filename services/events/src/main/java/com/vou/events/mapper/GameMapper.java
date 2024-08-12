package com.vou.events.mapper;

import com.vou.events.dto.GameDto;
import com.vou.events.entity.Game;

import org.springframework.stereotype.Service;

@Service
public class GameMapper {

    // Convert Game to GameDto
    public static GameDto toDto(Game game) {
        if (game == null) {
            return null;
        }

        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setImage(game.getImage());
        gameDto.setType(game.getType());
        gameDto.setItemSwappable(game.isItemSwappable());
        gameDto.setInstruction(game.getInstruction());

        return gameDto;
    }

    // Convert GameDto to Game
    public static Game toEntity(GameDto dto) {
        if (dto == null) {
            return null;
        }

        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());
        game.setImage(dto.getImage());
        game.setType(dto.getType());
        game.setItemSwappable(dto.isItemSwappable());
        game.setInstruction(dto.getInstruction());

        return game;
    }
}
