package com.vou.statistics.model;

import java.util.Date;

public interface Transaction {
    String getPlayerId();
    String getRecipientId();
    String getArtifactId();
    int getQuantity();
    String getTransactionType();
    Date getCreatedAt();
    void setCreatedAt(Date createdAt);
    Date getUpdatedAt();
    void setUpdatedAt(Date updatedAt);
    default String getEventId() { return null; }
    default void setRecipientId(String _recipientId) {}
    default void setArtifactId(String _artifactId) {}
    default void setEventId(String _eventId) {}
    default void onCreate() {
        Date now = new Date();
        setCreatedAt(now);
        setUpdatedAt(now);
    }
    default void onUpdate() {
        setUpdatedAt(new Date());
    }
}

