package com.vou.sessions.engine;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Record implements Serializable {
    protected long totalTime;
    protected long startPlayTime;
}
