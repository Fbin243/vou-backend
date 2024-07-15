package com.vou.games.service;

import com.vou.games.dto.GameDto;
import com.vou.games.entity.Game;
import com.vou.games.exception.NotFoundException;
import com.vou.games.mapper.GameMapper;
import com.vou.games.repository.GamesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GamesService implements IGamesService {
    private final GamesRepository gamesRepository;

    @Override
    public List<GameDto> fetchAllGames() {
        List<Game> games = gamesRepository.findAll();
        return games.stream()
                .map(GameMapper::toGameDto)
                .collect(Collectors.toList());
    }

    @Override
    public GameDto fetchGameById(Long id) {
        Game game = gamesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Game", "id", id.toString())
        );

        return GameMapper.toGameDto(game);
    }

    @Override
    public void createGame(GameDto gameDto) {
        Game game = GameMapper.toGame(gameDto);
        game.setId(null);
        gamesRepository.save(game);
    }

    @Override
    public void updateGame(GameDto gameDto) {
        // Check if game id exists or not
        gamesRepository.findById(gameDto.getId()).orElseThrow(
                () -> new NotFoundException("Game", "id", gameDto.getId().toString())
        );

        Game game = GameMapper.toGame(gameDto);
        gamesRepository.save(game);
    }

    @Override
    public void deleteGame(Long id) {
        Game game = gamesRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Game", "id", id.toString())
        );

        gamesRepository.delete(game);
    }
}
