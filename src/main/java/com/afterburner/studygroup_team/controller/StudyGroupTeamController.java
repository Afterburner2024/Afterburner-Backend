package com.afterburner.studygroup_team.controller;

import java.util.List;

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
import com.afterburner.studygroup_team.model.dto.StudyGroupTeamDTO;
import com.afterburner.studygroup_team.service.StudyGroupTeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/study-group/{studyGroupId}/member")
public class StudyGroupTeamController {

	private final StudyGroupTeamService studyGroupTeamService;

	@Autowired
	public StudyGroupTeamController(StudyGroupTeamService studyGroupTeamService) {
		this.studyGroupTeamService = studyGroupTeamService;
	}

	@PostMapping
	public ResponseEntity<?> addStudyGroupTeamMember(@PathVariable Integer studyGroupId,
		@Valid @RequestBody StudyGroupTeamDTO studyGroupTeamDTO) {

		if (studyGroupTeamDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		studyGroupTeamDTO.setStudyGroupTeamPostId(studyGroupId);

		if (studyGroupTeamDTO.getStudyGroupTeamUserId() == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body("스터디그룹에 추가할 user ID가 존재하지 않습니다.");
		}

		StudyGroupTeamDTO createdDTO = studyGroupTeamService.addMember(studyGroupTeamDTO);

		if (createdDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudyGroupTeamMember(@Valid @RequestBody StudyGroupTeamDTO studyGroupTeamDTO,
		@PathVariable("id") Integer id, @PathVariable Integer studyGroupId) {
		if (id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		studyGroupTeamDTO.setStudyGroupTeamPostId(studyGroupId);

		StudyGroupTeamDTO updatedDTO = studyGroupTeamService.updateMember(studyGroupTeamDTO, id);

		if (updatedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(updatedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudyGroupTeamMember(@PathVariable("id") Integer id,
		@PathVariable Integer studyGroupId) {
		if (id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		StudyGroupTeamDTO deletedDTO = studyGroupTeamService.quitMember(id);

		if (deletedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(deletedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@GetMapping
	public List<StudyGroupTeamDTO> getAllStudyGroupTeamMembers() {
		return studyGroupTeamService.allMemberList();
	}
}
