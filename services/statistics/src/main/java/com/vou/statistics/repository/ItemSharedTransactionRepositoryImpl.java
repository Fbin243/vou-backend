// package com.vou.statistics.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.stereotype.Repository;

// import com.vou.statistics.entity.ItemSharedTransaction;

// import static org.springframework.data.mongodb.core.query.Criteria.where;
// import static org.springframework.data.mongodb.core.query.Query.query;

// public class ItemSharedTransactionRepositoryImpl implements ItemSharedTransactionRepositoryCustom {

//     private final MongoTemplate mongoTemplate;

//     public ItemSharedTransactionRepositoryImpl(MongoTemplate mongoTemplate) {
//         this.mongoTemplate = mongoTemplate;
//     }
    
//     @Override
//     public Optional<ItemSharedTransaction> findTransactionByPlayerId(String playerId) {
//         return Optional.ofNullable(mongoTemplate.findOne(query(where("player_id").is(playerId)), ItemSharedTransaction.class));
//     }

//     @Override
//     public Optional<ItemSharedTransaction> findTransactionBySenderIdAndRecipientId(String playerId, String recipientId) {
//         return Optional.ofNullable(mongoTemplate.findOne(query(where("player_id").is(playerId).and("recipient_id").is(recipientId)), ItemSharedTransaction.class));
//     }

//     @Override
//     public List<ItemSharedTransaction> findTransactionsByPlayerId(String playerId) {
//         return mongoTemplate.find(query(where("player_id").is(playerId)), ItemSharedTransaction.class);
//     }

//     @Override
//     public List<ItemSharedTransaction> findTransactionsByRecipientId(String recipientId) {
//         return mongoTemplate.find(query(where("recipient_id").is(recipientId)), ItemSharedTransaction.class);
//     }

//     @Override
//     public List<ItemSharedTransaction> findTransactionsBySenderIdAndRecipientId(String playerId, String recipientId) {
//         return mongoTemplate.find(query(where("player_id").is(playerId).and("recipient_id").is(recipientId)), ItemSharedTransaction.class);
//     }

//     @Override
//     public List<ItemSharedTransaction> findTransactionsByArtifactId(String artifactId) {
//         return mongoTemplate.find(query(where("item_id").is(artifactId)), ItemSharedTransaction.class);
//     }
// }
