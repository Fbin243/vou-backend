package com.vou.users.service;

import com.vou.users.entity.Player;
import java.util.List;

public interface PlayerService {
    void savePlayer(Player thePlayer);
    Player findPlayerById(String theId);
    List<Player> findAllPlayers();
    void updatePlayer(Player thePlayer);
    void deletePlayerById(String theId);
    Player findPlayerByPhone(String phone);
    Player findByEmail(String email);

    List<Player> findManyPlayersByManyIds(List<String> ids);
    String requestTurns(String id, String phone, int turns);
    String acceptTurns(String id1, String id2, int turns);


}