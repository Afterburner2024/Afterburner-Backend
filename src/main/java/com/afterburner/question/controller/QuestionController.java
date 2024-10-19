package com.afterburner.question.controller;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.question.model.dto.QuestionDTO;
import com.afterburner.question.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

	private final QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@PostMapping
	public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
		if (questionDTO == null){
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus()).body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		QuestionDTO createdDTO = questionService.createPost(questionDTO);

		if (createdDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateQuestion(@Valid @RequestBody QuestionDTO questionDTO, @PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus()).body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		if (questionDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus()).body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		QuestionDTO updatedDTO = questionService.updatePost(questionDTO, id);

		if (updatedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(updatedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus()).body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		QuestionDTO deletedDTO = questionService.deletePost(id);

		if (deletedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(deletedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@GetMapping
	public List<QuestionDTO> getAllQuestions() {
		return questionService.allStudyGroupList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();

		if(id <= 0 || id == null) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus()).body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		QuestionDTO findDTO = questionService.detailPost(id);

		if (findDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(findDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

}
