package com.afterburner.studygroup.service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.model.entity.StudyRole;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.security.access.AccessDeniedException; // Spring Security 사용 시 권장

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {

	private final StudyGroupRepository studyGroupRepository;

	@Autowired
	public StudyGroupService(StudyGroupRepository studyGroupRepository) {
		this.studyGroupRepository = studyGroupRepository;
	}

	private StudyGroupDTO toDto(StudyGroupEntity entity) {
		StudyGroupDTO dto = new StudyGroupDTO();
		dto.setStudyGroupId(entity.getStudyGroupId());
		dto.setStudyGroupCategory(entity.getStudyGroupCategory());
		dto.setStudyGroupTitle(entity.getStudyGroupTitle());
		dto.setStudyGroupContent(entity.getStudyGroupContent());
		dto.setStudyGroupCreatedAt(entity.getStudyGroupCreatedAt());
		dto.setStudyGroupUpdatedAt(entity.getStudyGroupUpdatedAt());
		dto.setStudyGroupStatus(entity.getStudyGroupStatus());
		dto.setStudyGroupUserId(entity.getStudyGroupUserId());
		dto.setStudyGroupRole(entity.getStudyGroupRole());
		dto.setStudyGroupMembers(entity.getStudyGroupMembers());
		return dto;
	}

	@Transactional
	public StudyGroupDTO createPost(StudyGroupDTO studyGroupDTO) {
		// String currentUserId = ... ;
		// studyGroupDTO.setStudyGroupUserId(currentUserId);

		List<String> members = studyGroupDTO.getStudyGroupMembers();
		if (members != null) {
			members = members.stream()
			.filter(m -> !m.equals(String.valueOf(studyGroupDTO.getStudyGroupUserId())))
			.collect(Collectors.toList());
		}

		StudyGroupEntity entity = StudyGroupEntity.builder()
						.studyGroupCategory(studyGroupDTO.getStudyGroupCategory())
						.studyGroupTitle(studyGroupDTO.getStudyGroupTitle())
						.studyGroupContent(studyGroupDTO.getStudyGroupContent())
						.studyGroupStatus(studyGroupDTO.getStudyGroupStatus())
						.studyGroupUserId(studyGroupDTO.getStudyGroupUserId())
						.studyGroupRole(StudyRole.LEADER)
						.studyGroupMembers(members)
						.build();

		StudyGroupEntity savedEntity = studyGroupRepository.save(entity);
		return toDto(savedEntity);
	}

	@Transactional
	public StudyGroupDTO updatePost(StudyGroupDTO studyGroupDTO, Integer id) {
		String currentUserId = "testUser"; // 예시: 현재 로그인한 사용자 ID

		// 1. ID로 게시글을 조회하고, 없으면 예외를 발생.
		StudyGroupEntity entity = studyGroupRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));

		// 2. 게시글 작성자와 현재 로그인한 사용자가 동일한지 권한을 확인
		if (!entity.getStudyGroupUserId().equals(currentUserId)) {
			// Spring Security 사용 시 AccessDeniedException("권한이 없습니다.") 사용
			throw new SecurityException("게시글을 수정할 권한이 없습니다.");
		}

		// 3. DTO의 내용으로 엔티티의 값을 변경 (JPA의 Dirty Checking 활용)
		entity.setStudyGroupCategory(studyGroupDTO.getStudyGroupCategory());
		entity.setStudyGroupTitle(studyGroupDTO.getStudyGroupTitle());
		entity.setStudyGroupContent(studyGroupDTO.getStudyGroupContent());
		entity.setStudyGroupStatus(PostStatus.DEFAULT);

		return toDto(entity);
	}

	@Transactional
	public void deletePost(Integer id) {
//		String currentUserId = "testUser";

		StudyGroupEntity entity = studyGroupRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));

//		if (!entity.getStudyGroupUserId().equals(currentUserId)) {
//			throw new SecurityException("게시글을 삭제할 권한이 없습니다.");
//		}

		// 실제 데이터를 삭제하는 대신, 상태를 'DELETED'로 변경 (Soft Delete)
		entity.setStudyGroupStatus(PostStatus.DELETED);
		entity.setStudyGroupDeletedAt(LocalDateTime.now());
	}

	public List<StudyGroupDTO> allStudyGroupList() {
		return studyGroupRepository.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public StudyGroupDTO detailPost(Integer id) {
		return studyGroupRepository.findById(id)
				.map(this::toDto)
				.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id: " + id));
	}
}