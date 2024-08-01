package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.entity.Event;
import com.vou.events.entity.Item;
import com.vou.events.model.EventItemId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "events_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(EventItemId.class)
public class EventItem implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Item item;

    private int numberOfItem;

    private EventIntermediateTableStatus activeStatus;
}