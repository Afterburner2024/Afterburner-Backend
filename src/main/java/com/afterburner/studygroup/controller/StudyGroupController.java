package com.afterburner.studygroup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> createStudyGroup(@Valid @RequestBody StudyGroupDTO studyGroupDTO) {
		if (studyGroupDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		StudyGroupDTO createdDTO = studyGroupService.createPost(studyGroupDTO);

		if (createdDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudyGroup(@Valid @RequestBody StudyGroupDTO studyGroupDTO,
		@PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		if (studyGroupDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		StudyGroupDTO updatedDTO = studyGroupService.updatePost(studyGroupDTO, id);

		if (updatedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(updatedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudyGroup(@PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		StudyGroupDTO deletedDTO = studyGroupService.deletePost(id);

		if (deletedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(deletedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@GetMapping
	public List<StudyGroupDTO> getAllStudyGroups() {
		return studyGroupService.allStudyGroupList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();

		if (id <= 0 || id == null) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		StudyGroupDTO findDTO = studyGroupService.detailPost(id);

		if (findDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(findDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

}
