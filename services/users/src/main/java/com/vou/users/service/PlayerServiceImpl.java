package com.vou.users.service;

import com.vou.users.dao.PlayerRepository;
import com.vou.users.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
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
    public void updatePlayer(Player thePlayer) {
        playerRepository.save(thePlayer);
    }

    @Override
    @Transactional
    public void deletePlayerById(String theId) {
        playerRepository.deleteById(theId);
    }

    @Override
    public Player findPlayerByPhoneNumber(String phoneNumber) {
        return playerRepository.findByPhone(phoneNumber);
    }

    @Override
    public Player findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }
}