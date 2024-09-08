package com.vou.sessions.engine.shakinggame;

import com.vou.sessions.engine.Record;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShakingRecord extends Record implements Serializable {
    private int turns;
}
