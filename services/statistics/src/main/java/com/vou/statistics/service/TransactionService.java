package com.vou.statistics.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService<T extends Transaction> implements ITransactionService<T> {
    
    private TransactionRepository<T> transactionRepository;

    @Override
    public List<T> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public T getTransactionById(ObjectId id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<T> getTransactionsByPlayer(String senderId) {
        return transactionRepository.findTransactionsByPlayerId(senderId);
    }

    @Override
    public List<T> getTransactionsByRecipient(String recipientId) {
        return transactionRepository.findTransactionsByRecipientId(recipientId);
    }

    @Override
    public List<T> getTransactionsBySenderAndRecipient(String senderId, String recipientId) {
        return transactionRepository.findTransactionsByPlayerIdAndRecipientId(senderId, recipientId);
    }

    @Override
    public List<T> getTransactionsByArtifact(String artifactId) {
        return transactionRepository.findTransactionsByArtifactId(artifactId);
    }

    @Override
    public T saveItemTransaction(T transaction) {
        // transaction.setSwapDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<T> saveItemTransactions(List<T> transactions) {
        // for (T transaction : transactions) {
        //     transaction.setSwapDate(LocalDateTime.now());
        // }

        return transactionRepository.saveAll(transactions);
    }
}
