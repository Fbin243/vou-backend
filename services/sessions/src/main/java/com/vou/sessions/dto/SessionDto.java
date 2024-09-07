package com.vou.sessions.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SessionDto {
	private ObjectId id;
	private String eventId;
	private String gameId;
	private String brandId;
	private LocalDate date;
	private List<RecordDto> users;
}
