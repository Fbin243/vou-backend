package com.vou.games.controller;

import com.vou.games.dto.GameDto;
import com.vou.games.service.IGamesService;
import com.vou.pkg.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GamesController {
    private final IGamesService gamesService;

    @GetMapping("/games")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<GameDto> gameDtos = gamesService.fetchAllGames();
        return ResponseEntity.ok(gameDtos);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable("id") Long id) {
        GameDto gameDto = gamesService.fetchGameById(id);
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping("/games")
    public ResponseEntity<ResponseDto> createGame(@RequestBody GameDto gameDto) {
        gamesService.createGame(gameDto);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Game created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/games")
    public ResponseEntity<ResponseDto> updateGame(@RequestBody GameDto gameDto) {
        gamesService.updateGame(gameDto);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Game updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<ResponseDto> deleteGame(@PathVariable Long id) {
        gamesService.deleteGame(id);
        ResponseDto res = new ResponseDto(HttpStatus.OK, "Game deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
