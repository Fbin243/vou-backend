package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.PlayerItem;

import lombok.NoArgsConstructor;

@Repository
public interface PlayerItemRepository extends MongoRepository<PlayerItem, ObjectId> 
{
    List<PlayerItem> findByPlayerId(String playerId);
    List<PlayerItem> findByItemId(String itemId);
    Optional<PlayerItem> findByPlayerIdAndItemId(String playerId, String itemId);
    // List<PlayerItem> findByPlayerIdAndItemIds(String playerId, List<String> itemIds);
}
