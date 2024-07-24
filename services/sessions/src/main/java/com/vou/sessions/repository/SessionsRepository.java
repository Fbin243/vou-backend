package com.vou.sessions.repository;

import com.vou.sessions.entity.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionsRepository extends MongoRepository<Session, ObjectId> {
    Optional<Session> findSessionByEventIdAndGameId(String eventId, String gameId);
}
