package com.afterburner.user.controller;

import com.afterburner.common.response.ApiResponse;
import com.afterburner.user.model.UserDTO;
import com.afterburner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse<UserDTO.UserResponse>>> createUser(@RequestBody UserDTO.CreateUserRequest request) {
        return userService.createUser(request).thenApply(userResponse ->
                ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userResponse)));
    }

    @PutMapping("/{userId}")
    public CompletableFuture<ResponseEntity<ApiResponse<UserDTO.UserResponse>>> updateUser(@PathVariable Integer userId, @RequestBody UserDTO.UpdateUserRequest request) {
        return userService.updateUser(userId, request).thenApply(userResponse ->
                ResponseEntity.ok(ApiResponse.success(userResponse)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ApiResponse<List<UserDTO.UserResponse>>>> getAllUsers() {
        return userService.getAllUsers().thenApply(userResponses ->
                ResponseEntity.ok(ApiResponse.success(userResponses)));
    }

    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<ApiResponse<UserDTO.UserResponse>>> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId).thenApply(userResponse ->
                ResponseEntity.ok(ApiResponse.success(userResponse)));
    }

    @DeleteMapping("/{userId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Void>>> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId).thenApply(aVoid ->
                ResponseEntity.noContent().build());
    }
}
