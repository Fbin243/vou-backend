package com.vou.users.dao;

import com.vou.users.entity.Brand;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {

    Brand findByEmail(String email);

    @Query("SELECT b FROM Brand b WHERE b.email IN :emails")
    List<Brand> findManyBrandsByManyEmails(@Param("emails") List<String> emails);
}
