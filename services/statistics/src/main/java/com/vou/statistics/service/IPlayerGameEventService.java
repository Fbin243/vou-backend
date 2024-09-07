package com.vou.statistics.service;

import java.util.List;

import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerGameEventDto;
import com.vou.statistics.entity.PlayerGameEvent;

public interface IPlayerGameEventService {
    
    default List<PlayerDto> getPlayersByGameIdAndEventId(String gameId, String eventId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default List<PlayerDto> getPlayersEventId(String eventId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default PlayerGameEvent addPlayerGameEvent(PlayerGameEventDto playerGameEventDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
