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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

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



    // 파이어베이스 로그인
    public UserDTO.UserResponse firebaseLoginOrRegister(String idToken) {
        System.out.println("=== firebaseLoginOrRegister CALLED ===");
        try {
            // 1. 파이어베이스 토큰 검증 (이 줄에서 유효하지 않으면 Exception 발생)
            System.out.println("idToken from client: " + idToken);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            System.out.println("decodedToken: " + decodedToken);

            // 2. 토큰에서 이메일, 이름, 프로필 사진 추출
            String email = decodedToken.getEmail();
            String name = (String) decodedToken.getClaims().getOrDefault("name", "사용자");
            String picture = (String) decodedToken.getClaims().getOrDefault("picture", null);

            // 3. DB에서 이메일로 유저 조회
            User user = userRepository.findByUserEmail(email).orElse(null);

            // 4. 없으면 신규 생성
            if (user == null) {
                user = new User();
                user.setUserEmail(email);
                user.setUserName(name);
                user.setUserImage(picture);
                // 필요한 필드 초기화 가능
                user = userRepository.save(user);
            }

            // 5. UserResponse로 감싸서 반환
            return new UserDTO.UserResponse(user);

        } catch (Exception e) {
            // 검증 실패(토큰 만료/위조/잘못된 토큰 등)
            System.out.println("🔥🔥🔥 catch block entered!");
            e.printStackTrace();
            throw new RuntimeException("파이어베이스 토큰 검증 실패: " + e.getMessage(), e);
        }
    }
}
