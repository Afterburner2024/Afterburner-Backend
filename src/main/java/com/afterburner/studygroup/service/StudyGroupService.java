package com.afterburner.studygroup.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.common.enums.TeamMemberRole;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import com.afterburner.studygroup_team.model.dto.StudyGroupTeamDTO;
import com.afterburner.studygroup_team.service.StudyGroupTeamService;

import jakarta.transaction.Transactional;

@Service
public class StudyGroupService {

	private final StudyGroupRepository studyGroupRepository;
	private final StudyGroupTeamService studyGroupTeamService;

	@Autowired
	public StudyGroupService(StudyGroupRepository studyGroupRepository, StudyGroupTeamService studyGroupTeamService) {
		this.studyGroupRepository = studyGroupRepository;
		this.studyGroupTeamService = studyGroupTeamService;
	}

	@Transactional
	public StudyGroupDTO createPost(StudyGroupDTO studyGroupDTO) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리

		StudyGroupEntity entity = new StudyGroupEntity.Builder()
			.studyGroupCategory(studyGroupDTO.getStudyGroupCategory())
			.studyGroupTitle(studyGroupDTO.getStudyGroupTitle())
			.studyGroupContent(studyGroupDTO.getStudyGroupContent())
			.studyGroupStatus(studyGroupDTO.getStudyGroupStatus())
			.studyGroupUserId(studyGroupDTO.getStudyGroupUserId())
			.build();

		StudyGroupEntity savedEntity = studyGroupRepository.save(entity);

		if (savedEntity != null) {
			StudyGroupDTO savedDTO = new StudyGroupDTO();
			savedDTO.setStudyGroupId(savedEntity.getStudyGroupId());
			savedDTO.setStudyGroupCategory(savedEntity.getStudyGroupCategory());
			savedDTO.setStudyGroupTitle(savedEntity.getStudyGroupTitle());
			savedDTO.setStudyGroupContent(savedEntity.getStudyGroupContent());
			savedDTO.setStudyGroupCreatedAt(savedEntity.getStudyGroupCreatedAt());
			savedDTO.setStudyGroupUpdatedAt(savedEntity.getStudyGroupUpdatedAt());
			savedDTO.setStudyGroupStatus(savedEntity.getStudyGroupStatus());
			savedDTO.setStudyGroupUserId(savedEntity.getStudyGroupUserId());

			// 작성자를 팀장으로 새로운 team생성
			StudyGroupTeamDTO teamMemberDTO = new StudyGroupTeamDTO();
			teamMemberDTO.setStudyGroupTeamPostId(savedDTO.getStudyGroupId());
			teamMemberDTO.setStudyGroupTeamUserId(savedDTO.getStudyGroupUserId());
			teamMemberDTO.setStudyGroupTeamRole(TeamMemberRole.LEADER);
			studyGroupTeamService.addMember(teamMemberDTO);

			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public StudyGroupDTO updatePost(StudyGroupDTO studyGroupDTO, Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<StudyGroupEntity> studyGroupEntity = studyGroupRepository.findById(id);

		// 위에서 꺼낸 userId랑 조회한 게시글의 userId가 같은지 확인 -> 동일인이어야 수정 가능함

		// 같으면
		StudyGroupEntity entity = studyGroupEntity.get();
		entity.setStudyGroupCategory(studyGroupDTO.getStudyGroupCategory());
		entity.setStudyGroupTitle(studyGroupDTO.getStudyGroupTitle());
		entity.setStudyGroupContent(studyGroupDTO.getStudyGroupContent());

		StudyGroupEntity savedEntity = studyGroupRepository.save(entity);

		if (savedEntity != null) {
			StudyGroupDTO savedDTO = new StudyGroupDTO();
			savedDTO.setStudyGroupId(savedEntity.getStudyGroupId());
			savedDTO.setStudyGroupCategory(savedEntity.getStudyGroupCategory());
			savedDTO.setStudyGroupTitle(savedEntity.getStudyGroupTitle());
			savedDTO.setStudyGroupContent(savedEntity.getStudyGroupContent());
			savedDTO.setStudyGroupCreatedAt(savedEntity.getStudyGroupCreatedAt());
			savedDTO.setStudyGroupUpdatedAt(savedEntity.getStudyGroupUpdatedAt());
			savedDTO.setStudyGroupStatus(savedEntity.getStudyGroupStatus());
			savedDTO.setStudyGroupUserId(savedEntity.getStudyGroupUserId());

			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public StudyGroupDTO deletePost(Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<StudyGroupEntity> studyGroupEntity = studyGroupRepository.findById(id);

		// 위에서 꺼낸 userId랑 조회한 게시글의 userId가 같은지 확인 -> 동일인이어야 삭제 가능함
		// 같으면

		if (studyGroupEntity.isPresent()) { // 게시글 존재하면
			StudyGroupEntity entity = studyGroupEntity.get();
			entity.setStudyGroupStatus(PostStatus.DELETED); // 삭제상태로 변경
			entity.setStudyGroupDeletedAt(LocalDateTime.now());
			StudyGroupEntity savedEntity = studyGroupRepository.save(entity);

			StudyGroupDTO resultDTO = new StudyGroupDTO();
			resultDTO.setStudyGroupId(savedEntity.getStudyGroupId());
			resultDTO.setStudyGroupCategory(savedEntity.getStudyGroupCategory());
			resultDTO.setStudyGroupTitle(savedEntity.getStudyGroupTitle());
			resultDTO.setStudyGroupContent(savedEntity.getStudyGroupContent());
			resultDTO.setStudyGroupCreatedAt(savedEntity.getStudyGroupCreatedAt());
			resultDTO.setStudyGroupUpdatedAt(savedEntity.getStudyGroupUpdatedAt());
			resultDTO.setStudyGroupStatus(savedEntity.getStudyGroupStatus());
			resultDTO.setStudyGroupUserId(savedEntity.getStudyGroupUserId());

			return resultDTO;

		} else {
			return null;
		}
	}

	public List<StudyGroupDTO> allStudyGroupList() {
		List<StudyGroupEntity> entities = studyGroupRepository.findAll();
		List<StudyGroupDTO> studyGroupList = new ArrayList<>();

		if (entities.size() > 0) {
			for (StudyGroupEntity entity : entities) {
				StudyGroupDTO dto = new StudyGroupDTO();
				dto.setStudyGroupId(entity.getStudyGroupId());
				dto.setStudyGroupCategory(entity.getStudyGroupCategory());
				dto.setStudyGroupTitle(entity.getStudyGroupTitle());
				dto.setStudyGroupContent(entity.getStudyGroupContent());
				dto.setStudyGroupCreatedAt(entity.getStudyGroupCreatedAt());
				dto.setStudyGroupUpdatedAt(entity.getStudyGroupUpdatedAt());
				dto.setStudyGroupStatus(entity.getStudyGroupStatus());
				dto.setStudyGroupUserId(entity.getStudyGroupUserId());
				studyGroupList.add(dto);
			}
			return studyGroupList;
		}
		return new ArrayList<>();
	}

	public StudyGroupDTO detailPost(Integer id) {
		Optional<StudyGroupEntity> findEntity = studyGroupRepository.findById(id);
		StudyGroupDTO detailDTO = new StudyGroupDTO();

		if (findEntity.isPresent()) {
			detailDTO.setStudyGroupId(findEntity.get().getStudyGroupId());
			detailDTO.setStudyGroupCategory(findEntity.get().getStudyGroupCategory());
			detailDTO.setStudyGroupTitle(findEntity.get().getStudyGroupTitle());
			detailDTO.setStudyGroupContent(findEntity.get().getStudyGroupContent());
			detailDTO.setStudyGroupCreatedAt(findEntity.get().getStudyGroupCreatedAt());
			detailDTO.setStudyGroupUpdatedAt(findEntity.get().getStudyGroupUpdatedAt());
			detailDTO.setStudyGroupStatus(findEntity.get().getStudyGroupStatus());
			detailDTO.setStudyGroupUserId(findEntity.get().getStudyGroupUserId());
		}
		return detailDTO;
	}
}
