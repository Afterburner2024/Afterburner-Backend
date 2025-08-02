package com.afterburner.user.controller;

import com.afterburner.common.response.ApiResponse;
import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.user.model.UserDTO;
import com.afterburner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse<UserDTO.UserResponse>>> createUser(@RequestBody UserDTO.CreateUserRequest request) {
        return userService.createUser(request).thenApply(userResponse ->
                ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userResponse)));
    }

    // 파이어베이스 유저 로그인
    @PostMapping("/firebase-login")
    public ResponseEntity<ApiResponse<UserDTO.UserResponse>> firebaseLogin(
            @RequestHeader("Authorization") String authorization) {
        try {
            // 1. Bearer 토큰 파싱
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                System.out.println("🟡🟡🟡 토큰 없음 또는 Bearer 아님!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "No token provided"));
            }
            String idToken = authorization.substring(7);

            // 2. 서비스에 위임 (토큰 검증 + 유저 자동 생성/조회)
            UserDTO.UserResponse userResponse = userService.firebaseLoginOrRegister(idToken);

            // 3. 성공 응답
            return ResponseEntity.ok(
                    ApiResponse.success(userResponse)
            );
        } catch (Exception e) {
            // 4. 실패 응답
            System.out.println("🔥🔥🔥 CATCH BLOCK 진입!");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "토큰 검증 실패: " + e.getMessage()));
        }
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

    @GetMapping("/{userId}/projects")
    public CompletableFuture<ResponseEntity<ApiResponse<List<com.afterburner.project.model.ProjectDTO>>>> getUserProjects(@PathVariable Integer userId) {
        return userService.getUserProjects(userId).thenApply(projects ->
                ResponseEntity.ok(ApiResponse.success(projects)));
    }

    @GetMapping("/{userId}/participated-projects")
    public CompletableFuture<ResponseEntity<ApiResponse<List<com.afterburner.project.model.ProjectDTO>>>> getParticipatedProjects(@PathVariable Integer userId) {
        return userService.getUserParticipatedProjects(userId).thenApply(projects ->
                ResponseEntity.ok(ApiResponse.success(projects)));
    }

    @GetMapping("/{userId}/studies")
    public CompletableFuture<ResponseEntity<ApiResponse<List<com.afterburner.studygroup.model.dto.StudyGroupDTO>>>> getUserStudies(@PathVariable Integer userId) {
        return userService.getUserStudies(userId).thenApply(studies ->
                ResponseEntity.ok(ApiResponse.success(studies)));
    }

    @GetMapping("/{userId}/participated-studies")
    public CompletableFuture<ResponseEntity<ApiResponse<List<com.afterburner.studygroup.model.dto.StudyGroupDTO>>>> getParticipatedStudies(@PathVariable Integer userId) {
        return userService.getUserParticipatedStudies(userId).thenApply(studies ->
                ResponseEntity.ok(ApiResponse.success(studies)));
    }

    @GetMapping("/{userId}/questions")
    public CompletableFuture<ResponseEntity<ApiResponse<List<QnaDTO>>>> getUserQuestions(@PathVariable Integer userId) {
        return userService.getUserQuestions(userId).thenApply(questions ->
                ResponseEntity.ok(ApiResponse.success(questions)));
    }
}
