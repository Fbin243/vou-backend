package com.vou.events.model;

import lombok.NoArgsConstructor;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Optional: to exclude null fields from serialization
@JsonSerialize
public class NotificationInfo {
    private String id;

    private String title;

    private String description;

    private String imageUrl;

    // Constructor to generate id automatically
    public NotificationInfo(String title, String description, String imageUrl) {
        this.id = UUID.randomUUID().toString(); // Auto-generate unique ID
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
