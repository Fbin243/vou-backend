package com.vou.sessions.utils;

import java.time.Instant;

public class Utils {
    public static long now() {
        return Instant.now().getEpochSecond();
    }
}
