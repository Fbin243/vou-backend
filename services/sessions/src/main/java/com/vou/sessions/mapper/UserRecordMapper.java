package com.vou.sessions.mapper;

import com.vou.sessions.dto.UserRecordDto;
import com.vou.sessions.entity.UserRecord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRecordMapper {
//    UserRecordMapper INSTANCE = Mappers.getMapper(UserRecordMapper.class);

    UserRecordDto toDto(UserRecord userRecord);

    UserRecord toEntity(UserRecordDto userRecordDto);

    List<UserRecordDto> toDtoList(List<UserRecord> userRecords);

    List<UserRecord> toEntityList(List<UserRecordDto> userRecordDtos);
}
