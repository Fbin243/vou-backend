package com.vou.events.model;

import com.vou.events.entity.Event;
import com.vou.events.entity.Game;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class EventGameId implements Serializable {
    private Event event;

    private Game game;

    // equals() and hashCode()
}