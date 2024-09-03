package com.vou.sessions.repository;

import com.vou.sessions.entity.SessionEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SessionsRepository extends MongoRepository<SessionEntity, ObjectId> {
	Optional<SessionEntity> findSessionById(String id);
	
	Optional<SessionEntity> findSessionByEventIdAndGameIdAndDate(String eventId, String gameId, LocalDate date);
}
