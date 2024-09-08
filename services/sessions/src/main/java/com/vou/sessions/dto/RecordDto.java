package com.vou.sessions.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class RecordDto {
	protected String userId;
	protected long totalTime;
}
