package com.vou.users.dao;

import com.vou.users.entity.User;
import com.vou.users.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole(UserRole role);

    Optional<User> findByAccountId(String accountId);
}
