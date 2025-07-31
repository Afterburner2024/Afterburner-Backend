package com.afterburner.studygroup.controller;

import com.afterburner.common.codes.SuccessCode;
import com.afterburner.studygroup.model.dto.StudyGroupDTO;
import com.afterburner.studygroup.service.StudyGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-group")
public class StudyGroupController {

	private final StudyGroupService studyGroupService;

	@Autowired
	public StudyGroupController(StudyGroupService studyGroupService) {
		this.studyGroupService = studyGroupService;
	}

	// 등록
	@PostMapping
	public ResponseEntity<StudyGroupDTO> createStudyGroup(@Valid @RequestBody StudyGroupDTO studyGroupDTO) {
		StudyGroupDTO createdDTO = studyGroupService.createPost(studyGroupDTO);
		return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
	}

	// 수정
	// 게시글 등록한 유저만 수정 가능
	@PutMapping("/{id}")
	public ResponseEntity<StudyGroupDTO> updateStudyGroup(@PathVariable("id") Integer id, @Valid @RequestBody StudyGroupDTO studyGroupDTO) {
		StudyGroupDTO updatedDTO = studyGroupService.updatePost(studyGroupDTO, id);
		return ResponseEntity.status(SuccessCode.UPDATE.getStatus()).body(updatedDTO);
	}

	// 삭제
	// 게시글 등록한 유저와 관리자만 삭제 가능
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudyGroup(@PathVariable("id") Integer id) {
		studyGroupService.deletePost(id);
		return ResponseEntity.status(SuccessCode.DELETE.getStatus()).build();
	}

	// 조회
	@GetMapping
	public ResponseEntity<List<StudyGroupDTO>> getAllStudyGroups() {
		List<StudyGroupDTO> studyGroupList = studyGroupService.allStudyGroupList();
		return ResponseEntity.ok(studyGroupList);
	}

	// 상세조회
	@GetMapping("/{id}")
	public ResponseEntity<StudyGroupDTO> getStudyGroupDetail(@PathVariable("id") Integer id) {
		StudyGroupDTO findDTO = studyGroupService.detailPost(id);
		return ResponseEntity.ok(findDTO);
	}
}