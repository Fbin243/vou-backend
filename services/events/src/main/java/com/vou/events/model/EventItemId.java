package com.vou.events.model;

import com.vou.events.entity.Event;
import com.vou.events.entity.Item;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class EventItemId implements Serializable {
    private Event event;

    private Item item;

    // equals() and hashCode()
}