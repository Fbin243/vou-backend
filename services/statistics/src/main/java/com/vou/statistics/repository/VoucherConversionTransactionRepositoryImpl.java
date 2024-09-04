// package com.vou.statistics.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.mongodb.core.MongoTemplate;

// import com.vou.statistics.entity.VoucherConversionTransaction;

// import static org.springframework.data.mongodb.core.query.Criteria.where;
// import static org.springframework.data.mongodb.core.query.Query.query;

// public class VoucherConversionTransactionRepositoryImpl implements VoucherConversionTransactionRepositoryCustom {
    
//     private final MongoTemplate mongoTemplate;

//     public VoucherConversionTransactionRepositoryImpl(MongoTemplate mongoTemplate) {
//         this.mongoTemplate= mongoTemplate;
//     }

//     @Override
//     public Optional<VoucherConversionTransaction> findTransactionByPlayerId(String playerId) {
//         return Optional.ofNullable(mongoTemplate.findOne(query(where("player_id").is(playerId)), VoucherConversionTransaction.class));
//     }

//     @Override
//     public List<VoucherConversionTransaction> findTransactionsByPlayerId(String playerId) {
//         return mongoTemplate.find(query(where("player_id").is(playerId)), VoucherConversionTransaction.class);
//     }

//     @Override
//     public List<VoucherConversionTransaction> findTransactionsByArtifactId(String artifactId) {
//         return mongoTemplate.find(query(where("voucher_id").is(artifactId)), VoucherConversionTransaction.class);
//     }
    
// }
