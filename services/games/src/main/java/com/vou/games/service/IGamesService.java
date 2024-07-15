package com.vou.games.service;

import com.vou.games.dto.GameDto;

import java.util.List;

public interface IGamesService {
    List<GameDto> fetchAllGames();

    GameDto fetchGameById(Long id);

    void createGame(GameDto gameDto);

    void updateGame(GameDto gameDto);

    void deleteGame(Long id);
}
