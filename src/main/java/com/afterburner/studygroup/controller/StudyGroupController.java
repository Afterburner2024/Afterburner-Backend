package com.afterburner.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.service.StudyGroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/study-group")
public class StudyGroupController {

	private final StudyGroupService studyGroupService;

	@Autowired
	public StudyGroupController(StudyGroupService studyGroupService) {
		this.studyGroupService = studyGroupService;
	}

	@PostMapping
	public ResponseEntity<?> createStudyGroupPost(@Valid @RequestBody StudyGroupDTO studyGroupDTO) {
		System.out.println("컨트롤러실행");
		if (studyGroupDTO == null){
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus()).body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}
		System.out.println("디티오"+studyGroupDTO);

		StudyGroupDTO createdDTO = studyGroupService.createPost(studyGroupDTO);

		if (createdDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

}
