package com.afterburner.studygroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.repository.StudyGroupRepository;

import jakarta.transaction.Transactional;

@Service
public class StudyGroupService {

	private final StudyGroupRepository studyGroupRepository;

	@Autowired
	public StudyGroupService(StudyGroupRepository studyGroupRepository) {
		this.studyGroupRepository = studyGroupRepository;
	}

	@Transactional
	public StudyGroupDTO createPost(StudyGroupDTO studyGroupDTO) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		System.out.println("서비스실행");

		StudyGroupEntity entity = new StudyGroupEntity.Builder()
			.studyGroupCategory(studyGroupDTO.getStudyGroupCategory())
			.studyGroupTitle(studyGroupDTO.getStudyGroupTitle())
			.studyGroupContent(studyGroupDTO.getStudyGroupContent())
			.studyGroupStatus(studyGroupDTO.getStudyGroupStatus())
			.studyGroupUserId(studyGroupDTO.getStudyGroupUserId())
			.build();

		StudyGroupEntity savedEntity = studyGroupRepository.save(entity);
		System.out.println("엔터티" +savedEntity);

		if (savedEntity != null) {
			StudyGroupDTO savedDTO = new StudyGroupDTO();
			savedDTO.setStudyGroupId(savedEntity.getStudyGroupId());
			savedDTO.setStudyGroupCategory(savedEntity.getStudyGroupCategory());
			savedDTO.setStudyGroupTitle(savedEntity.getStudyGroupTitle());
			savedDTO.setStudyGroupContent(savedEntity.getStudyGroupContent());
			savedDTO.setStudyGroupCreatedAt(savedEntity.getStudyGroupCreatedAt());
			savedDTO.setStudyGroupUpdatedAt(savedEntity.getStudyGroupUpdatedAt());
			savedDTO.setStudyGroupStatus(savedEntity.getStudyGroupStatus());
			savedDTO.setStudyGroupUserId(savedDTO.getStudyGroupUserId());

			return savedDTO;
		} else {
			return null;
		}
	}
}
