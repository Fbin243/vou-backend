package com.vou.users.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize // Add this annotation to support deserialization
public class NotificationInfo {
    private String id;

    private String title;

    private String description;

    private String imageUrl;

    public NotificationInfo(String title, String description, String imageUrl) {
        this.id = UUID.randomUUID().toString(); // Auto-generate unique ID
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}