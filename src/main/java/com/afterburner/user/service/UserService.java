package com.afterburner.user.service;

import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.model.entity.QnaEntity;
import com.afterburner.qna.repository.QnaRepository;
import com.afterburner.user.exception.UserEmailAlreadyExistsException;
import com.afterburner.user.exception.UserNotFoundException;
import com.afterburner.user.model.User;
import com.afterburner.user.model.UserDTO;
import com.afterburner.user.model.UserGrade;
import com.afterburner.user.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.afterburner.project.model.Project;
import com.afterburner.project.model.ProjectDTO;
import com.afterburner.project.repository.ProjectRepository;
import com.afterburner.projectTeam.model.ProjectTeam;
import com.afterburner.projectTeam.repository.ProjectTeamRepository;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.repository.StudyGroupMemberRepository;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
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
    private final ProjectRepository projectRepository;
    private final ProjectTeamRepository projectTeamRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMemberRepository studyGroupMemberRepository;
    private final QnaRepository qnaRepository;

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
        user.setUserGrade(UserGrade.USER); // 기본 등급을 USER로 설정

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

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<ProjectDTO>> getUserProjects(Integer userId) {
        List<Project> projects = projectRepository.findByProjectUserId(userId);
        List<ProjectDTO> dtos = projects.stream()
                .map(project -> ProjectDTO.builder()
                        .projectId(project.getProjectId())
                        .projectTitle(project.getProjectTitle())
                        .projectSummary(project.getProjectSummary())
                        .projectContent(project.getProjectContent())
                        .projectLink(project.getProjectLink())
                        .projectCreatedAt(project.getProjectCreatedAt())
                        .projectUpdatedAt(project.getProjectUpdatedAt())
                        .projectDeletedAt(project.getProjectDeletedAt())
                        .projectFinishedAt(project.getProjectFinishedAt())
                        .projectStatus(project.getProjectStatus())
                        .projectTechStack(project.getProjectTechStack())
                        .projectRecruitmentRoles(project.getProjectRecruitmentRoles())
                        .projectUserId(project.getProjectUserId())
                        .projectUserName(userRepository.findById(project.getProjectUserId()).map(User::getUserName).orElse(null))
                        .projectRegion(project.getProjectRegion())
                        .build())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(dtos);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<ProjectDTO>> getUserParticipatedProjects(Integer userId) {
        List<ProjectTeam> teams = projectTeamRepository.findByProjectTeamUserId(userId);
        List<ProjectDTO> dtos = teams.stream()
                .map(team -> projectRepository.findById(team.getProjectTeamPostId()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .map(this::mapProjectToDTO)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(dtos);
    }

    private ProjectDTO mapProjectToDTO(Project p) {
        return ProjectDTO.builder()
                .projectId(p.getProjectId())
                .projectTitle(p.getProjectTitle())
                .projectSummary(p.getProjectSummary())
                .projectContent(p.getProjectContent())
                .projectLink(p.getProjectLink())
                .projectCreatedAt(p.getProjectCreatedAt())
                .projectUpdatedAt(p.getProjectUpdatedAt())
                .projectDeletedAt(p.getProjectDeletedAt())
                .projectFinishedAt(p.getProjectFinishedAt())
                .projectStatus(p.getProjectStatus())
                .projectTechStack(p.getProjectTechStack())
                .projectRecruitmentRoles(p.getProjectRecruitmentRoles())
                .projectUserId(p.getProjectUserId())
                .projectUserName(userRepository.findById(p.getProjectUserId()).map(User::getUserName).orElse(null))
                .projectRegion(p.getProjectRegion())
                .build();
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<StudyGroupDTO>> getUserStudies(Integer userId) {
        List<StudyGroupEntity> studies = studyGroupRepository.findByStudyGroupUserId(userId);
        List<StudyGroupDTO> dtos = studies.stream()
                .map(study -> StudyGroupDTO.builder()
                        .studyGroupId(study.getStudyGroupId())
                        .studyGroupCategory(study.getStudyGroupCategory())
                        .studyGroupTitle(study.getStudyGroupTitle())
                        .studyGroupContent(study.getStudyGroupContent())
                        .studyGroupCreatedAt(study.getStudyGroupCreatedAt())
                        .studyGroupUpdatedAt(study.getStudyGroupUpdatedAt())
                        .studyGroupDeletedAt(study.getStudyGroupDeletedAt())
                        .studyGroupFinishedAt(study.getStudyGroupFinishedAt())
                        .studyGroupStatus(study.getStudyGroupStatus())
                        .studyGroupMembers(study.getStudyGroupMembers())
                        .studyGroupUserId(study.getStudyGroupUserId())
                        .studyGroupUserName(userRepository.findById(study.getStudyGroupUserId()).map(User::getUserName).orElse(null))
                        .studyGroupRole(study.getStudyGroupRole())
                        .build())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(dtos);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<StudyGroupDTO>> getUserParticipatedStudies(Integer userId) {
        List<com.afterburner.studygroup.model.entity.StudyGroupMemberEntity> members = studyGroupMemberRepository.findByStudyMemberUserId(userId);
        List<StudyGroupDTO> dtos = members.stream()
                .map(m -> studyGroupRepository.findById(m.getStudyGroupId()).orElse(null))
                .filter(java.util.Objects::nonNull)
                .map(this::mapStudyGroupToDTO)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(dtos);
    }

    private StudyGroupDTO mapStudyGroupToDTO(StudyGroupEntity study) {
        return StudyGroupDTO.builder()
                .studyGroupId(study.getStudyGroupId())
                .studyGroupCategory(study.getStudyGroupCategory())
                .studyGroupTitle(study.getStudyGroupTitle())
                .studyGroupContent(study.getStudyGroupContent())
                .studyGroupCreatedAt(study.getStudyGroupCreatedAt())
                .studyGroupUpdatedAt(study.getStudyGroupUpdatedAt())
                .studyGroupDeletedAt(study.getStudyGroupDeletedAt())
                .studyGroupFinishedAt(study.getStudyGroupFinishedAt())
                .studyGroupStatus(study.getStudyGroupStatus())
                .studyGroupMembers(study.getStudyGroupMembers())
                .studyGroupUserId(study.getStudyGroupUserId())
                .studyGroupUserName(userRepository.findById(study.getStudyGroupUserId()).map(User::getUserName).orElse(null))
                .studyGroupRole(study.getStudyGroupRole())
                .build();
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<QnaDTO>> getUserQuestions(Integer userId) {
        List<QnaEntity> qnas = qnaRepository.findByQnaUserId(userId);
        List<QnaDTO> dtos = qnas.stream()
                .map(qna -> QnaDTO.builder()
                        .qnaId(qna.getQnaId())
                        .qnaTitle(qna.getQnaTitle())
                        .qnaContent(qna.getQnaContent())
                        .qnaCreatedAt(qna.getQnaCreatedAt())
                        .qnaUpdatedAt(qna.getQnaUpdatedAt())
                        .qnaDeletedAt(qna.getQnaDeletedAt())
                        .qnaUserId(qna.getQnaUserId())
                        .build())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(dtos);
    }
}
