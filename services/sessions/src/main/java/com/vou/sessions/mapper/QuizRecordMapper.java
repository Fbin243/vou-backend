package com.vou.sessions.mapper;

import com.vou.sessions.engine.quizgame.QuizRecordDto;
import com.vou.sessions.engine.quizgame.QuizRecordEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizRecordMapper {

    QuizRecordDto toDto(QuizRecordEntity quizRecordEntity);

    QuizRecordEntity toEntity(QuizRecordDto quizRecordDto);

    List<QuizRecordDto> toDtoList(List<QuizRecordEntity> quizRecordEntities);

    List<QuizRecordEntity> toEntityList(List<QuizRecordDto> quizRecordDtos);
}
