package com.devteria.identity.service;

import java.util.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devteria.identity.dto.OtpStatus;
import com.devteria.identity.dto.request.*;
import com.devteria.identity.dto.response.OtpDataResponse;
import com.devteria.identity.dto.response.OtpResponseDto;
import com.devteria.identity.dto.response.UserResponse;
import com.devteria.identity.entity.Role;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import com.devteria.identity.mapper.UserMapper;
import com.devteria.identity.repository.RoleRepository;
import com.devteria.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    //    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;
    //    ProfileClient profileClient;

    SmsService smsService; // Inject SmsService

    private final Map<String, String> otpMap = new HashMap<>(); // Store OTPs
    private OtpService otpService;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                Role existingRole =
                        roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
                roles.add(existingRole);
            }
            user.setRoles(roles);
        } else {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }

        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void sendOtp(String username, String phoneNumber) {
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setUsername(username);
        otpRequest.setPhoneNumber(phoneNumber);
        OtpResponseDto response = smsService.sendSMS(otpRequest); // Call the updated sendSMS method

        if (response.getStatus() == OtpStatus.FAILED) {
            throw new AppException(ErrorCode.OTP_SEND_FAILED); // Handle failed OTP sending
        }
    }

    public UserResponse verifyOtp(UserCreationRequest request) {
        OtpDataResponse otpEntity = otpService.getOtpByUsername(request.getUsername()); // Fetch OTP by username
        if (otpEntity != null
                && otpEntity.getOtp() != null
                && otpEntity.getOtp().equals(request.getOtp())) { // Check if OTP is valid
            otpService.deleteOtpByUsername(request.getUsername()); // Delete OTP after verification
            // Proceed to create user if OTP is valid
            //            UserCreationRequest request = new UserCreationRequest(); // Create a new request object
            //            request.setUsername(username);

            // You may need to fetch other user details if necessary
            return createUser(request); // Create the user
        } else {
            throw new AppException(ErrorCode.INVALID_OTP); // Handle invalid OTP
        }
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //        var roles = roleRepository.findAllById(request.getRoles());
        //        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('admin')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('admin')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
