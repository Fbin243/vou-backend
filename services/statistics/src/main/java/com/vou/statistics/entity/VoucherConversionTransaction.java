package com.vou.statistics.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.vou.statistics.dto.ItemId_Quantity;
import com.vou.statistics.model.Transaction;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_VOUCHER_CONVERSION;

@Document(collection = "voucher_conversion_transactions")
@Data
@Getter
@Setter
public class VoucherConversionTransaction implements Transaction {
    @Id
    private ObjectId id;

    @Field(value = "player_id")
    private String playerId;

    @Field(value = "voucher_id")
    private String artifactId;

    @Field(value = "event_id")
    private String eventId;

    @Field(value = "items")
    private List<ItemId_Quantity> items;

    @Field(value = "transaction_date")
    private LocalDateTime transactionDate;

    @Field(value = "quantity")
    private int quantity;

    @Field(value = "transaction_type")
    private String transactionType = TRANSACTION_TYPE_VOUCHER_CONVERSION;


    @Field(value = "created_at")
    private Date createdAt;

    @Field(value = "updated_at")
    private Date updatedAt;

    public String getPlayerId() {
        return playerId;
    }

    public String getRecipientId() {
        return playerId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String _eventId) {
        this.eventId = _eventId;
    }

    public void setArtifactId(String _artifactId) {
        this.artifactId = _artifactId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onCreate() {
        Transaction.super.onCreate();
    }

    @PreUpdate
    public void onUpdate() {
        Transaction.super.onUpdate();
    }
}
