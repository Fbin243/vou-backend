package com.vou.users.controller;

import com.vou.users.entity.User;
import com.vou.users.entity.UserRole;
import com.vou.users.service.UserService;
import com.vou.users.exception.NotFoundException;
import com.vou.users.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getRole() == null) {
            throw new BadRequestException("Role is required");
        }
        user.setRole(UserRole.valueOf(user.getRole().name().toUpperCase()));
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(userService.findUsersByRole(role));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        if (userService.findUserById(id) == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (userService.findUserById(id) == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
