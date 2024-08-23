package com.vou.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTokenId implements Serializable {
    
    @Column(name = "email", nullable = false)
    private String userId;

    @Column(name = "token", nullable = false)
    private String token;
}
