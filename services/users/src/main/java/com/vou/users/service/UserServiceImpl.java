package com.vou.users.service;

import com.vou.users.entity.User;
import com.vou.users.entity.UserRole;
import com.vou.users.dao.UserRepository;
import com.vou.users.exception.AppException;
import com.vou.users.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        if (user.getRole() == null) {
            throw new AppException(ErrorCode.INVALID_KEY); 
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)); 
    }

    @Override
    public User findUserByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)); 
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED); 
        }
        return users;
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public List<User> findUsersByRole(UserRole role) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .toList();
        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED); 
        }
        return users;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_KEY); 
        }
        if (!userRepository.existsById(user.getId())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED); 
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('admin')")
    public void deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED); 
        }
        userRepository.deleteById(id);
    }
}