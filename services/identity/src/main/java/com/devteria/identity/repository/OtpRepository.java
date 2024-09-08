package com.devteria.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteria.identity.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByUsername(String username);
}
