package com.vou.events.entity;

import com.vou.events.model.EventBrandId;
import com.vou.events.common.EventIntermediateTableStatus;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "events_brands")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(EventBrandId.class)
public class EventBrand implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // @Id
    // @ManyToOne(cascade = CascadeType.MERGE)
    // @JoinColumn(name = "brand_id", nullable = false)
    // private Brand brand;

    @Id
    @Column(name = "brand_id", nullable = false)
    private String brand_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private EventIntermediateTableStatus activeStatus;
}