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
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Brand brand;

    private EventIntermediateTableStatus activeStatus;
}