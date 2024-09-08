package com.vou.events.entity;

import com.vou.events.common.EventIntermediateTableStatus;
import com.vou.events.model.EventGameId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "events_games")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(EventGameId.class)
public class EventGame implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // @Id
    // @ManyToOne(cascade = CascadeType.MERGE)
    // @JoinColumn(name = "game_id", nullable = false)
    // private Game game;

    @Id
    @Column(name = "game_id", nullable = false)
    private Long game_id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private EventIntermediateTableStatus activeStatus;
}