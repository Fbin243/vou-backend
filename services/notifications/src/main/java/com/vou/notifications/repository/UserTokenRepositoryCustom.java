package com.vou.notifications.repository;

import com.vou.notifications.entity.UserToken;

public interface UserTokenRepositoryCustom {
    public UserToken findByToken(String token);
    public UserToken findByUserId(String userId);
    public String findTokenByUserId(String userId);
    public UserToken findByUserIdAndToken(String userId, String token);
}
