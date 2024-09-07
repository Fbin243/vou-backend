package com.vou.statistics.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "players_games_events")
@Data
public class PlayerGameEvent {

    @Id
    private ObjectId id;

    @Field(value = "player_id")
    private String playerId;

    @Field(value = "game_id")
    private String gameId;

    @Field(value = "event_id")
    private String eventId;
}
