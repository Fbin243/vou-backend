package com.vou.users.controller;

import com.vou.users.entity.Player;
import com.vou.users.entity.User;
import com.vou.users.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService thePlayerService) {
        playerService = thePlayerService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAllPlayers();
    }

    @GetMapping("/{playerId}")
    public Player getPlayer(@PathVariable String playerId) {
        return playerService.findPlayerById(playerId);
    }

    @PostMapping
    public Player addPlayer(@RequestBody Player thePlayer) {
        thePlayer.setId("0"); // to force a save of new item instead of update
        playerService.savePlayer(thePlayer);
        return thePlayer;
    }

    @PatchMapping("/{id}")
    public Player updatePlayer(@PathVariable String id, @RequestBody Player player) {
        if (playerService.findPlayerById(id) == null) {
            throw new RuntimeException("Player not found with id: " + id);
        }
        player.setId(id);
        playerService.savePlayer(player);
        return player;
    }

    @DeleteMapping("/{playerId}")
    public String deletePlayer(@PathVariable String playerId) {
        playerService.deletePlayerById(playerId);
        return "Deleted player id - " + playerId;
    }

    @GetMapping("/phone/{phone}")
    public Player getPlayerByPhone(@PathVariable String phone) {
        return playerService.findPlayerByPhone(phone);
    }

    @GetMapping("/email/{email}")
    public Player getPlayerByEmail(@PathVariable String email) {
        return playerService.findByEmail(email);
    }

    @PostMapping("public/ids")
    public List<Player> getManyPlayersByManyIds(@RequestBody List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("Ids are required");
        }
        List<Player> result = playerService.findManyPlayersByManyIds(ids);
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("No player found with ids: " + ids);
        }
        return result;
    }
}
