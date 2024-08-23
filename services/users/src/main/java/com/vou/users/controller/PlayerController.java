package com.vou.users.controller;

import com.vou.users.entity.Player;
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

    @PatchMapping
    public Player updatePlayer(@RequestBody Player thePlayer) {
        playerService.updatePlayer(thePlayer);
        return thePlayer;
    }

    @DeleteMapping("/{playerId}")
    public String deletePlayer(@PathVariable String playerId) {
        playerService.deletePlayerById(playerId);
        return "Deleted player id - " + playerId;
    }

    @GetMapping("/phone/{phoneNumber}")
    public Player getPlayerByPhoneNumber(@PathVariable String phoneNumber) {
        return playerService.findPlayerByPhoneNumber(phoneNumber);
    }

    @GetMapping("/email/{email}")
    public Player getPlayerByEmail(@PathVariable String email) {
        return playerService.findByEmail(email);
    }
}
