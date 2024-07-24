package com.vou.sessions.mapper;

import com.vou.sessions.dto.SessionDto;
import com.vou.sessions.entity.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
//    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    Session toSession(SessionDto sessionDto);

    SessionDto toSessionDto(Session session);
}
