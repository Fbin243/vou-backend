package com.vou.statistics.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "likes")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    private String id;
    private String userId;
    private String likeableType; // event, post, player, etc.
    private String likeableId;
    private LocalDateTime createdAt;

    public Like(String userId, String likeableType, String likeableId) {
        this.userId = userId;
        this.likeableType = likeableType;
        this.likeableId = likeableId;
        this.createdAt = LocalDateTime.now();
    }
}
