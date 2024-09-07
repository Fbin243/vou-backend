package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.PlayerGameEvent;

@Repository
public interface PlayerGameEventRepository extends MongoRepository<PlayerGameEvent, ObjectId> 
{
    List<PlayerGameEvent> findByPlayerId(String playerId);
    List<PlayerGameEvent> findByGameId(String gameId);
    List<PlayerGameEvent> findByEventId(String eventId);
    List<PlayerGameEvent> findByGameIdAndEventId(String gameId, String eventId);
    Optional<PlayerGameEvent> findByPlayerIdAndGameIdAndEventId(String playerId, String gameId, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndGameIds(String playerId, List<String> gameIds);
    // List<PlayerGameEvent> findByPlayerIdAndEventIds(String playerId, List<String> eventIds);
    // List<PlayerGameEvent> findByGameIdAndEventIds(String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdAndEventIds(String playerId, String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdsAndEventIds(String playerId, List<String> gameIds, List<String> eventIds);
    // List<PlayerGameEvent> findByGameIdAndEventId(String gameId, String eventId);
    // List<PlayerGameEvent> findByGameIdAndEventIds(String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndEventId(String playerId, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndEventIds(String playerId, List<String> eventIds);
    // List<PlayerGameEvent> findByGameIdAndEventIds(String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdAndEventId(String playerId, String gameId, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdsAndEventId(String playerId, List<String> gameIds, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdAndEventIds(String playerId, String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdsAndEventIds(String playerId, List<String> gameIds, List<String> eventIds);
    // List<PlayerGameEvent> findByGameIdAndEventIds(String gameId, List<String> eventIds);
    // List<PlayerGameEvent> findByPlayerIdAndEventId(String playerId, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndEventIds(String playerId, List<String> eventIds);
    // List<PlayerGameEvent> findByGameIdAndEventId(String gameId, String eventId);
    // List<PlayerGameEvent> findByPlayerIdAndGameIdAndEventId    
}
