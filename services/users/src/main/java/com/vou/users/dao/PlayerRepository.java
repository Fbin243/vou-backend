package com.vou.users.dao;

import com.vou.users.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    Player findByPhone(String phone);
    Player findByEmail(String email);
    @Query("SELECT p FROM Player p WHERE p.id IN :ids")
    List<Player> findManyPlayersByManyIds(@Param("ids") List<String> ids);
}

