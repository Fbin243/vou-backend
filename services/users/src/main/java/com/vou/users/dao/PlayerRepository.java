package com.vou.users.dao;

import com.vou.users.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {

    Player findByPhone(String phone);
    Player findByEmail(String email);
}

