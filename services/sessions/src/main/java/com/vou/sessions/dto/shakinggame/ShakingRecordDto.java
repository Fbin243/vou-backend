package com.vou.sessions.dto.shakinggame;

import com.vou.sessions.dto.RecordDto;
import lombok.Data;

@Data
public class ShakingRecordDto extends RecordDto {
    private long turns;
}
