package com.vou.sessions.mapper;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.entity.SessionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RecordMapper.class})
public interface SessionMapper {
	SessionEntity toSession(SessionDto sessionDto);
	
	SessionDto toSessionDto(SessionEntity sessionEntity);
}
