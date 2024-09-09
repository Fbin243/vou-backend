package com.vou.users.service;

import com.vou.users.dao.PlayerRepository;
import com.vou.users.entity.Brand;
import com.vou.users.entity.Player;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository thePlayerRepository) {
        playerRepository = thePlayerRepository;
    }

    @Override
    @Transactional
    public void savePlayer(Player thePlayer) {
        playerRepository.save(thePlayer);
    }

    @Override
    public Player findPlayerById(String theId) {
        Optional<Player> result = playerRepository.findById(theId);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Did not find player id - " + theId);
        }
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePlayer(Player player) {
        Player existingPlayer = playerRepository.findById(player.getId())
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + player.getId()));

        // Update only fields that are provided
        if (player.getFullName() != null && !player.getFullName().isEmpty()) {
            existingPlayer.setFullName(player.getFullName());
        }
        if (player.getUsername() != null && !player.getUsername().isEmpty()) {
            existingPlayer.setUsername(player.getUsername());
        }
        if (player.getAccountId() != null && !player.getAccountId().isEmpty()) {
            existingPlayer.setAccountId(player.getAccountId());
        }
        if (player.getEmail() != null && !player.getEmail().isEmpty()) {
            existingPlayer.setEmail(player.getEmail());
        }
        if (player.getPhone() != null && !player.getPhone().isEmpty()) {
            existingPlayer.setPhone(player.getPhone());
        }
        if (player.getRole() != null) {
            existingPlayer.setRole(player.getRole());
        }
        if (player.isStatus()) {
            existingPlayer.setStatus(player.isStatus());
        }
        if (player.getGender() != null && !player.getGender().isEmpty()) {
            existingPlayer.setGender(player.getGender());
        }
        if (player.getFacebookAccount() != null && !player.getFacebookAccount().isEmpty()) {
            existingPlayer.setFacebookAccount(player.getFacebookAccount());
        }
        if (player.getDateOfBirth() != null && !player.getDateOfBirth().isEmpty()) {
            existingPlayer.setDateOfBirth(player.getDateOfBirth());
        }
        if (player.getAvatar() != null && !player.getAvatar().isEmpty()) {
            existingPlayer.setAvatar(player.getAvatar());
        }
        existingPlayer.setTurns(player.getTurns());
        playerRepository.save(existingPlayer);
    }

    @Override
    @Transactional
    public void deletePlayerById(String theId) {
        playerRepository.deleteById(theId);
    }

    @Override
    public Player findPlayerByPhone(String phone) {
        return playerRepository.findByPhone(phone);
    }

    @Override
    public Player findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    @Override
    public List<Player> findManyPlayersByManyIds(List<String> theIds) {
        return playerRepository.findManyPlayersByManyIds(theIds);
    }

    @Override
    public String requestTurns(String id, String phone, int turns) {
        Player player1 = findPlayerById(id);
        Player player2 = findPlayerByPhone(phone);

        if (player2 == null) {
            return "Player with phone " + phone + " not found.";
        }

        String message = player1.getFullName() + " has just sent a request asking for " + turns + " game turns from you.";
        String kafkaMessage = String.format("{\"id1\":\"%s\", \"turns\":%d, \"message\":\"%s\"}", id, turns, message);
        kafkaTemplate.send("turnsRequestTopic", player2.getId(), kafkaMessage);

        return "Request sent to " + player2.getFullName();
    }

    @Override
    @Transactional
    public String acceptTurns(String id1, String id2, int turns) {
        Player player1 = findPlayerById(id1);
        Player player2 = findPlayerById(id2);

        if (player1 == null || player2 == null) {
            return "Player not found.";
        }

        if (player2.getTurns() < turns) {
            return "Not enough turns to give.";
        }

        player1.setTurns(player1.getTurns() + turns);
        player2.setTurns(player2.getTurns() - turns);

        savePlayer(player1);
        savePlayer(player2);

        return "Turns transferred successfully.";
    }
}
