package com.vou.games.mapper;

import com.vou.games.dto.GameDto;
import com.vou.games.entity.Game;

public class GameMapper {
    public static Game toGame(GameDto gameDto) {
        Game game = new Game();
        game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setImage(gameDto.getImage());
        game.setType(gameDto.getType());
        game.setItemSwappable(gameDto.isItemSwappable());
        game.setInstruction(gameDto.getInstruction());
        return game;
    }

    public static GameDto toGameDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setImage(game.getImage());
        gameDto.setType(game.getType());
        gameDto.setInstruction(game.getInstruction());
        return gameDto;
    }
}
