package com.vou.statistics.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.vou.statistics.client.UsersServiceClient;
import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerGameEventDto;
import com.vou.statistics.entity.PlayerGameEvent;
import com.vou.statistics.mapper.PlayerGameEventMapper;
import com.vou.statistics.repository.PlayerGameEventRepository;

import com.vou.statistics.service.IPlayerGameEventService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class PlayerGameEventService implements IPlayerGameEventService {
    @Autowired
    private PlayerGameEventRepository playerGameEventRepository;

    @Autowired
    private UsersServiceClient usersServiceClient;

    public PlayerGameEventService(PlayerGameEventRepository playerGameEventRepository) {
        this.playerGameEventRepository = playerGameEventRepository;
    }

    @Override
    public List<PlayerDto> getPlayersByGameIdAndEventId(String gameId, String eventId) {
        List<PlayerGameEvent> playerGameEvents = playerGameEventRepository.findByGameIdAndEventId(gameId, eventId);
        
        return usersServiceClient.getManyPlayersByManyIds(playerGameEvents.stream().map(PlayerGameEvent::getPlayerId).collect(Collectors.toList()));
    }

    @Override
    public List<PlayerDto> getPlayersEventId(String eventId) {
        List<PlayerGameEvent> playerGameEvents = playerGameEventRepository.findByEventId(eventId);
        
        return usersServiceClient.getManyPlayersByManyIds(playerGameEvents.stream().map(PlayerGameEvent::getPlayerId).collect(Collectors.toList()));
    }

    @Override
    public PlayerGameEvent addPlayerGameEvent(PlayerGameEventDto playerGameEventDto) {
        return playerGameEventRepository.save(PlayerGameEventMapper.toEntity(playerGameEventDto));
    }
}
