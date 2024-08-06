package com.vou.users.service;

import com.vou.users.entity.User;
import com.vou.users.entity.UserRole;
import com.vou.users.dao.UserRepository;
import com.vou.users.exception.NotFoundException;
import com.vou.users.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        if (user.getRole() == null  || user.getRole().equals("")) {
            throw new BadRequestException("User role is required");
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(UserRole role) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .toList();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found with role: " + role);
        }
        return users;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new BadRequestException("User ID is required for update");
        }
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(String id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
