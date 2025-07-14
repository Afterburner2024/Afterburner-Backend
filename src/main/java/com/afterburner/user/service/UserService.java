package com.afterburner.user.service;

import com.afterburner.user.exception.UserEmailAlreadyExistsException;
import com.afterburner.user.exception.UserNotFoundException;
import com.afterburner.user.model.User;
import com.afterburner.user.model.UserDTO;
import com.afterburner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Async
    @Transactional
    public CompletableFuture<UserDTO.UserResponse> createUser(UserDTO.CreateUserRequest request) {
        // 이메일 중복 확인
        if (userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setUserPhoneNumber(request.getUserPhoneNumber());
        user.setUserEmail(request.getUserEmail());
        user.setNote(request.getNote());
        user.setUserTechStacks(request.getUserTechStacks());
        user.setUserImage(request.getUserImage());

        User savedUser = userRepository.save(user);
        return CompletableFuture.completedFuture(new UserDTO.UserResponse(savedUser));
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO.UserResponse> updateUser(Integer userId, UserDTO.UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.setUserName(request.getUserName());
        user.setUserPhoneNumber(request.getUserPhoneNumber());
        user.setNote(request.getNote());
        user.setUserTechStacks(request.getUserTechStacks());
        user.setUserImage(request.getUserImage());

        User updatedUser = userRepository.save(user);
        return CompletableFuture.completedFuture(new UserDTO.UserResponse(updatedUser));
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<UserDTO.UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO.UserResponse> userResponses = users.stream()
                .map(UserDTO.UserResponse::new)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(userResponses);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<UserDTO.UserResponse> getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return CompletableFuture.completedFuture(new UserDTO.UserResponse(user));
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.delete();
        userRepository.save(user);
        return CompletableFuture.completedFuture(null);
    }
}
