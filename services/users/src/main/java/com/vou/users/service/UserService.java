package com.vou.users.service;

import com.vou.users.entity.User;
import com.vou.users.entity.UserRole;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserById(String id);
    User findUserByAccountId(String accountId);
    List<User> findAllUsers();
    List<User> findUsersByRole(UserRole role);
    User updateUser(User user);
    void deleteUserById(String id);
}