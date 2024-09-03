package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vou.statistics.entity.PlayerVoucher;

@Repository
public interface PlayerVoucherRepository extends MongoRepository<PlayerVoucher, ObjectId> {
    List<PlayerVoucher> findByPlayerId(String playerId);
    List<PlayerVoucher> findByVoucherId(String voucherId);
    Optional<PlayerVoucher> findByPlayerIdAndVoucherId(String playerId, String voucherId);
    // List<PlayerVoucher> findByPlayerIdAndVoucherIds(String playerId, List<String> voucherIds);
}
