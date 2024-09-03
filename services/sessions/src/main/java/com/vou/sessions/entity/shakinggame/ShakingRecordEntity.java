package com.vou.sessions.entity.shakinggame;

import com.vou.sessions.entity.RecordEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ShakingRecordEntity extends RecordEntity {
    @Field(value = "turns")
    private long turns;
}
