package com.vou.events.model;

import com.vou.events.entity.Event;
import com.vou.events.entity.Brand;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class EventBrandId implements Serializable {
    private Event event;

    private Brand brand;

    // equals() and hashCode()
}