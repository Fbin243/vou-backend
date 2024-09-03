package com.vou.statistics.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.vou.statistics.dto.VoucherItemsConversionInfo;
import com.vou.statistics.model.Transaction;

/**
 * Service interface for managing transactions.
 */
public interface ITransactionService<T extends Transaction> {
    
    /**
     * Get all transactions.
     * 
     * @return List of transactions.
     */
    default List<T> getAllTransactions() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get transaction by id.
     * 
     * @param transactionId Transaction id.
     * @return Transaction.
     */
    default T getTransactionById(ObjectId id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get transactions by sender.
     * 
     * @param senderId Sender id.
     * @return List of transactions.
     */
    default List<T> getTransactionsByPlayer(String senderId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get transactions by recipient.
     * 
     * @param senderId Recipient id.
     * @return List of transactions.
     */
    default List<T> getTransactionsByRecipient(String recipientId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get transactions by sender and recipient.
     * 
     * @param senderId Sender id.
     * @param recipientId Recipient id.
     * @return List of transactions.
     */
    default List<T> getTransactionsBySenderAndRecipient(String senderId, String recipientId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get transactions by artifact.
     * 
     * @param artifactId Artifact id.
     * @return List of transactions.
     */
    default List<T> getTransactionsByArtifact(String artifactId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Process item transaction.
     * 
     * @param transaction Transaction.
     * @return True if transaction was processed successfully, false otherwise.
     */
    default boolean processItemTransaction(T transaction) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Process item transactions.
     * 
     * @param transactions List of transactions.
     * @return True if transactions were processed successfully, false otherwise.
     */
    default boolean processItemTransactions(List<T> transactions) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Save item transaction.
     * 
     * @param transaction Transaction.
     * @return Saved transaction.
     */
    default T saveItemTransaction(T transaction) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Save item transactions.
     * 
     * @param transactions List of transactions.
     * @return List of saved transactions.
     */
    default List<T> saveItemTransactions(List<T> transactions) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Process voucher transaction.
     * 
     * @param voucherItemsConversionInfo List of voucher items conversion info.
     * @return True if transaction was processed successfully, false otherwise.
     */
    default Boolean processVoucherTransaction(VoucherItemsConversionInfo voucherItemsConversionInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
