package com.afterburner.studygroup_team.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import com.afterburner.studygroup_team.model.dto.StudyGroupTeamDTO;
import com.afterburner.studygroup_team.model.entity.StudyGroupTeamEntity;
import com.afterburner.studygroup_team.repository.StudyGroupTeamRepository;

import jakarta.transaction.Transactional;

@Service
public class StudyGroupTeamService {

	private final StudyGroupTeamRepository studyGroupTeamRepository;
	private final StudyGroupRepository studyGroupRepository;

	@Autowired
	public StudyGroupTeamService(StudyGroupTeamRepository studyGroupTeamRepository,
		StudyGroupRepository studyGroupRepository) {
		this.studyGroupTeamRepository = studyGroupTeamRepository;
		this.studyGroupRepository = studyGroupRepository;
	}

	@Transactional
	public StudyGroupTeamDTO addMember(StudyGroupTeamDTO studyGroupTeamDTO) {
		// 로그인한 userId 추가해야함
		StudyGroupTeamEntity entity = new StudyGroupTeamEntity();

		StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(studyGroupTeamDTO.getStudyGroupTeamPostId())
			.orElseThrow(() -> new RuntimeException("스터디 그룹을 찾을 수 없습니다."));

		entity.setStudyGroupTeamPostId(studyGroupEntity);
		entity.setStudyGroupTeamUserId(studyGroupTeamDTO.getStudyGroupTeamUserId());
		entity.setStudyGroupTeamRole(studyGroupTeamDTO.getStudyGroupTeamRole());

		StudyGroupTeamEntity savedEntity = studyGroupTeamRepository.save(entity);

		if (savedEntity != null) {
			StudyGroupTeamDTO savedDTO = new StudyGroupTeamDTO();
			savedDTO.setStudyGroupTeamId(savedEntity.getStudyGroupTeamId());
			savedDTO.setStudyGroupTeamUserId(savedEntity.getStudyGroupTeamUserId());
			savedDTO.setStudyGroupTeamPostId(studyGroupTeamDTO.getStudyGroupTeamPostId());
			savedDTO.setStudyGroupTeamRole(savedEntity.getStudyGroupTeamRole());
			savedDTO.setStudyGroupJoinedAt(savedEntity.getStudyGroupJoinedAt());
			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public StudyGroupTeamDTO updateMember(StudyGroupTeamDTO studyGroupTeamDTO, Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<StudyGroupTeamEntity> studyGroupEntity = studyGroupTeamRepository.findById(id);

		Optional<StudyGroupTeamEntity> studyGroupTeamEntityOptional = studyGroupTeamRepository.findById(id);

		if (!studyGroupTeamEntityOptional.isPresent()) {
			throw new RuntimeException("해당 팀원을 찾을 수 없습니다.");
		}

		StudyGroupTeamEntity entity = studyGroupTeamEntityOptional.get();
		entity.setStudyGroupTeamRole(studyGroupTeamDTO.getStudyGroupTeamRole());
		entity.setStudyGroupTeamUserId(studyGroupTeamDTO.getStudyGroupTeamUserId());

		StudyGroupTeamEntity savedEntity = studyGroupTeamRepository.save(entity);

		if (savedEntity != null) {
			StudyGroupTeamDTO savedDTO = new StudyGroupTeamDTO();
			savedDTO.setStudyGroupTeamId(savedEntity.getStudyGroupTeamId());
			savedDTO.setStudyGroupTeamUserId(savedEntity.getStudyGroupTeamUserId());
			savedDTO.setStudyGroupTeamPostId(savedEntity.getStudyGroupTeamPostId().getStudyGroupId()); // Post ID 매핑
			savedDTO.setStudyGroupTeamRole(savedEntity.getStudyGroupTeamRole());
			savedDTO.setStudyGroupJoinedAt(savedEntity.getStudyGroupJoinedAt());

			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public StudyGroupTeamDTO quitMember(Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<StudyGroupTeamEntity> studyGroupEntity = studyGroupTeamRepository.findById(id);

		Optional<StudyGroupTeamEntity> studyGroupTeamEntityOptional = studyGroupTeamRepository.findById(id);

		if (!studyGroupTeamEntityOptional.isPresent()) {
			throw new RuntimeException("해당 팀원을 찾을 수 없습니다.");
		}

		StudyGroupTeamEntity entity = studyGroupTeamEntityOptional.get();
		entity.setStudyGroupQuitedAt(LocalDateTime.now());

		StudyGroupTeamEntity savedEntity = studyGroupTeamRepository.save(entity);

		if (savedEntity != null) {
			StudyGroupTeamDTO resultDTO = new StudyGroupTeamDTO();
			resultDTO.setStudyGroupTeamId(savedEntity.getStudyGroupTeamId());
			resultDTO.setStudyGroupTeamUserId(savedEntity.getStudyGroupTeamUserId());
			resultDTO.setStudyGroupTeamPostId(savedEntity.getStudyGroupTeamPostId().getStudyGroupId());
			resultDTO.setStudyGroupTeamRole(savedEntity.getStudyGroupTeamRole());
			resultDTO.setStudyGroupJoinedAt(savedEntity.getStudyGroupJoinedAt());
			resultDTO.setStudyGroupJoinedAt(savedEntity.getStudyGroupQuitedAt());
			return resultDTO;
		} else {
			return null;
		}
	}

	public List<StudyGroupTeamDTO> allMemberList() {
		List<StudyGroupTeamEntity> entities = studyGroupTeamRepository.findAll();
		List<StudyGroupTeamDTO> studyGroupList = new ArrayList<>();

		for (StudyGroupTeamEntity entity : entities) {
			StudyGroupTeamDTO dto = new StudyGroupTeamDTO();
			dto.setStudyGroupTeamId(entity.getStudyGroupTeamId());
			dto.setStudyGroupTeamUserId(entity.getStudyGroupTeamUserId());
			dto.setStudyGroupTeamPostId(entity.getStudyGroupTeamPostId().getStudyGroupId()); // Post ID 매핑
			dto.setStudyGroupTeamRole(entity.getStudyGroupTeamRole());
			dto.setStudyGroupJoinedAt(entity.getStudyGroupJoinedAt());
			studyGroupList.add(dto);
		}
		return studyGroupList;
	}
}
