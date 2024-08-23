package com.vou.events.model;

import lombok.NoArgsConstructor;

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
}
