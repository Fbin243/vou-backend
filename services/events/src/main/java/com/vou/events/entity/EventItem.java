package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "number_of_item")
    private int numberOfItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private EventIntermediateTableStatus activeStatus;
}