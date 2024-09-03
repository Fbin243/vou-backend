package com.vou.statistics.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vou.statistics.model.Transaction;

@Repository
public interface TransactionRepository<T extends Transaction> extends MongoRepository<T, ObjectId> {
    Optional<T> findTransactionByPlayerId(String playerId);
    Optional<T> findTransactionByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<T> findTransactionsByPlayerId(String playerId);
    List<T> findTransactionsByRecipientId(String recipientId);
    List<T> findTransactionsByPlayerIdAndRecipientId(String playerId, String recipientId);
    List<T> findTransactionsByArtifactId(String artifactId);
}
