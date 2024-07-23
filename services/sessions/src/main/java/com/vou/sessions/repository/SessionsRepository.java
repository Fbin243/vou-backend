package com.vou.sessions.repository;

import com.vou.sessions.entity.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends MongoRepository<Session, ObjectId> {
}
