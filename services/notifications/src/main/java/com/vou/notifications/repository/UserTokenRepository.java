package com.vou.notifications.repository;

import com.vou.notifications.entity.UserToken;
import com.vou.notifications.model.UserTokenId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UserTokenId>, UserTokenRepositoryCustom {

    UserToken findByUserId(String userId);

    UserToken findByToken(String token);

    String findTokenByUserId(String userId);

    UserToken findByUserIdAndToken(String userId, String token);
}