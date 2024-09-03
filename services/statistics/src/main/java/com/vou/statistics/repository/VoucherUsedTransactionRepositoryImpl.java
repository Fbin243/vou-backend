// package com.vou.statistics.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.mongodb.core.MongoTemplate;

// import com.vou.statistics.entity.VoucherUsedTransaction;

// import static org.springframework.data.mongodb.core.query.Criteria.where;
// import static org.springframework.data.mongodb.core.query.Query.query;

// public class VoucherUsedTransactionRepositoryImpl implements VoucherUsedTransactionRepositoryCustom {
    
//     private final MongoTemplate mongoTemplate;

//     public VoucherUsedTransactionRepositoryImpl(MongoTemplate mongoTemplate) {
//         this.mongoTemplate= mongoTemplate;
//     }
    
//     @Override
//     public Optional<VoucherUsedTransaction> findTransactionByPlayerId(String playerId) {
//         return Optional.ofNullable(mongoTemplate.findOne(query(where("player_id").is(playerId)), VoucherUsedTransaction.class));
//     }
    
//     @Override
//     public List<VoucherUsedTransaction> findTransactionsByPlayerId(String playerId) {
//         return mongoTemplate.find(query(where("player_id").is(playerId)), VoucherUsedTransaction.class);
//     }

//     @Override
//     public List<VoucherUsedTransaction> findTransactionsByArtifactId(String artifactId) {
//         return mongoTemplate.find(query(where("voucher_id").is(artifactId)), VoucherUsedTransaction.class);
//     }
// }
