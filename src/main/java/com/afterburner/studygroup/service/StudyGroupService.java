package com.afterburner.studygroup.service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.model.entity.StudyRole;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import com.afterburner.user.model.User;
import com.afterburner.user.model.UserGrade;
import com.afterburner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
// import org.springframework.security.access.AccessDeniedException; // Spring Security 사용 시 권장

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {

	private final StudyGroupRepository studyGroupRepository;
	private final UserRepository userRepository;

	@Autowired
	public StudyGroupService(StudyGroupRepository studyGroupRepository, UserRepository userRepository) {
		this.studyGroupRepository = studyGroupRepository;
		this.userRepository = userRepository;
	}

	// StudyGroupEntity → StudyGroupDTO 로 변환하는 메서드
	private StudyGroupDTO toDto(StudyGroupEntity entity) {
		StudyGroupDTO dto = new StudyGroupDTO();
		dto.setStudyGroupId(entity.getStudyGroupId()); // ID 설정
		dto.setStudyGroupCategory(entity.getStudyGroupCategory()); // 카테고리 설정
		dto.setStudyGroupTitle(entity.getStudyGroupTitle()); // 제목 설정
		dto.setStudyGroupContent(entity.getStudyGroupContent()); // 내용 설정
		dto.setStudyGroupCreatedAt(entity.getStudyGroupCreatedAt()); // 생성일자
		dto.setStudyGroupUpdatedAt(entity.getStudyGroupUpdatedAt()); // 수정일자
		dto.setStudyGroupStatus(entity.getStudyGroupStatus()); // 게시 상태 설정
		dto.setStudyGroupUserId(entity.getStudyGroupUserId()); // 작성자 ID 설정

		// 작성자 이름을 userRepository를 통해 조회
		dto.setStudyGroupUserName(userRepository.findById(entity.getStudyGroupUserId())
				.map(User::getUserName).orElse(null));

		dto.setStudyGroupRole(entity.getStudyGroupRole()); // 역할(LEADER 등)
		dto.setStudyGroupMembers(entity.getStudyGroupMembers()); // 참여 멤버 목록
		return dto;
	}

	// 스터디 그룹 생성
	@Transactional
	public StudyGroupDTO createPost(StudyGroupDTO studyGroupDTO) {
		// 현재 로그인한 사용자의 ID 가져오기
		Integer currentUserId = getCurrentUserId();

		// DTO에 현재 사용자 ID 설정 (작성자 ID)
		studyGroupDTO.setStudyGroupUserId(currentUserId);

		// 멤버 리스트에서 자신(작성자)을 제외한 사용자만 필터링
		List<String> members = studyGroupDTO.getStudyGroupMembers();
		if (members != null) {
			members = members.stream()
					.filter(m -> !m.equals(String.valueOf(studyGroupDTO.getStudyGroupUserId())))
					.collect(Collectors.toList());
		}

		// DTO로부터 Entity 생성
		StudyGroupEntity entity = StudyGroupEntity.builder()
				.studyGroupCategory(studyGroupDTO.getStudyGroupCategory())
				.studyGroupTitle(studyGroupDTO.getStudyGroupTitle())
				.studyGroupContent(studyGroupDTO.getStudyGroupContent())
				.studyGroupStatus(studyGroupDTO.getStudyGroupStatus())
				.studyGroupUserId(studyGroupDTO.getStudyGroupUserId())
				.studyGroupRole(StudyRole.LEADER) // 생성자는 리더로 지정
				.studyGroupMembers(members)
				.build();

		// 저장하고 저장된 엔티티를 DTO로 변환하여 반환
		StudyGroupEntity savedEntity = studyGroupRepository.save(entity);
		return toDto(savedEntity);
	}

	// 게시글 수정
	@Transactional
	public StudyGroupDTO updatePost(StudyGroupDTO studyGroupDTO, Integer id) {
		Integer currentUserId = getCurrentUserId(); // 현재 사용자 ID

		// 수정할 게시글을 DB에서 조회
		StudyGroupEntity entity = studyGroupRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));

		// 현재 로그인한 사용자와 게시글 작성자 비교
		if (!entity.getStudyGroupUserId().equals(currentUserId)) {
			throw new SecurityException("게시글을 수정할 권한이 없습니다.");
		}

		// DTO 내용을 엔티티에 반영 (변경 감지 이용)
		entity.setStudyGroupCategory(studyGroupDTO.getStudyGroupCategory());
		entity.setStudyGroupTitle(studyGroupDTO.getStudyGroupTitle());
		entity.setStudyGroupContent(studyGroupDTO.getStudyGroupContent());
		entity.setStudyGroupStatus(PostStatus.DEFAULT); // 상태 초기화

		return toDto(entity); // 수정된 엔티티를 DTO로 반환
	}

	// 게시글 삭제 (소프트 삭제 방식)
	@Transactional
	public void deletePost(Integer id) {
		Integer currentUserId = getCurrentUserId(); // 현재 사용자 ID

		// 삭제할 게시글 조회
		StudyGroupEntity entity = studyGroupRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));

		// 작성자 또는 관리자만 삭제 가능
		if (!entity.getStudyGroupUserId().equals(currentUserId) && !isCurrentUserAdmin()) {
			throw new SecurityException("게시글을 삭제할 권한이 없습니다.");
		}

		// 삭제 대신 상태만 변경하고 삭제 시간 저장
		entity.setStudyGroupStatus(PostStatus.DELETED);
		entity.setStudyGroupDeletedAt(LocalDateTime.now());
	}

	// 전체 스터디 그룹 리스트 조회
	public List<StudyGroupDTO> allStudyGroupList() {
		return studyGroupRepository.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	// 게시글 상세 조회
	public StudyGroupDTO detailPost(Integer id) {
		return studyGroupRepository.findById(id)
				.map(this::toDto)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));
	}

	// 현재 로그인한 사용자의 ID를 반환
	private Integer getCurrentUserId() {
		// 현재 로그인한 사용자의 인증 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증되지 않은 경우 예외 발생
		if (authentication == null || !authentication.isAuthenticated()
				|| "익명의 유저".equals(authentication.getPrincipal())) {
			throw new SecurityException("User is not authenticated.");
		}

		// 인증 정보에서 사용자 이메일 추출
		String userEmail = authentication.getName();

		// 이메일로 사용자 조회 (없으면 예외 발생)
		User user = userRepository.findByUserEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다.: " + userEmail));

		return user.getUserId(); // 사용자 ID 반환
	}

	// 현재 로그인한 사용자가 관리자 권한인지 확인
	private boolean isCurrentUserAdmin() {
		// 인증 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증되지 않았으면 false
		if (authentication == null || !authentication.isAuthenticated()
				|| "익명의 유저".equals(authentication.getPrincipal())) {
			return false;
		}

		// 사용자 이메일로 사용자 조회
		String userEmail = authentication.getName();
		User user = userRepository.findByUserEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException("유저의 이메일을 찾을 수 없습니다.: " + userEmail));

		// 관리자 권한인지 확인
		return user.getUserGrade() == UserGrade.ADMIN;
	}
}