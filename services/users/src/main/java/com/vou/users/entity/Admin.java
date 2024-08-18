package com.vou.users.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
@DiscriminatorValue("admin")
public class Admin extends User {

    // Constructors, getters, and setters

    public Admin() {
    }

    public Admin(String fullName, String username, String accountId, String email, String phone, UserRole role, boolean status) {
        super(fullName, username, accountId, email, phone, role, status);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", accountId='" + getAccountId() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", role='" + getRole() + '\'' +
                ", status='" + isStatus() + '\'' +
                '}';
    }
}

