package com.vou.games.entity;

import com.vou.pkg.entity.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@NoArgsConstructor
@Getter
@Setter
public class Game extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "type")
    private String type;

    @Column(name = "item_swappable")
    private boolean itemSwappable;

    @Column(name = "instruction")
    private String instruction;
}
