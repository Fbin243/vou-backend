package com.vou.users.controller;

import com.vou.users.entity.Admin;
import com.vou.users.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService theAdminService) {
        adminService = theAdminService;
    }

    @GetMapping
    public List<Admin> findAll() {
        return adminService.findAllAdmins();
    }

    @GetMapping("/{adminId}")
    public Admin getAdmin(@PathVariable String adminId) {
        return adminService.findAdminById(adminId);
    }

    @PostMapping
    public Admin addAdmin(@RequestBody Admin theAdmin) {
        theAdmin.setId("0"); // to force a save of new item instead of update
        adminService.saveAdmin(theAdmin);
        return theAdmin;
    }

    
    @PatchMapping
    public Admin updateAdmin(@RequestBody Admin theAdmin) {
        adminService.updateAdmin(theAdmin);
        return theAdmin;
    }

    @DeleteMapping("/{adminId}")
    public String deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdminById(adminId);
        return "Deleted admin id - " + adminId;
    }
}
