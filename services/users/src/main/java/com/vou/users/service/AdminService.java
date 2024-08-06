package com.vou.users.service;

import com.vou.users.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin theAdmin);
    Admin findAdminById(String theId);
    List<Admin> findAllAdmins();
    void updateAdmin(Admin theAdmin);
    void deleteAdminById(String theId);
}
