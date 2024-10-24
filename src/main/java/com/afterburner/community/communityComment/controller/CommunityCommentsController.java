package com.afterburner.community.communityComment.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.community.communityComment.model.CommunityCommentsDTO;
import com.afterburner.community.communityComment.service.CommunityCommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/community/{communityId}/comment")
public class CommunityCommentsController {

	private final CommunityCommentsService communityCommentsService;

	@Autowired
	public CommunityCommentsController(CommunityCommentsService communityCommentsService) {
		this.communityCommentsService = communityCommentsService;
	}

	// 댓글 등록
	@PostMapping
	public ResponseEntity<?> createComment(@PathVariable("communityId") Integer communityId,
		@RequestBody @Valid CommunityCommentsDTO communityCommentsDTO,
		@RequestParam String email) {
		communityCommentsDTO.setCommunityPostId(communityId);
		CommunityCommentsDTO createdComment = communityCommentsService.createComment(communityCommentsDTO, email);

		if (createdComment != null) {
			ApiResponse<CommunityCommentsDTO> response = new ApiResponse.Builder<CommunityCommentsDTO>()
				.statusCode(SuccessCode.INSERT.getStatus())
				.message(SuccessCode.INSERT.getMessage())
				.result(createdComment)
				.build();
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(response);
		} else {
			return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
				.body(ErrorCode.INSERT_ERROR.getMessage());
		}
	}

	// 댓글 목록 조회
	@GetMapping
	public ResponseEntity<?> getCommentsByPostId(@PathVariable("communityId") Integer communityId) {
		List<CommunityCommentsDTO> comments = communityCommentsService.getCommentsByPostId(communityId);
		ApiResponse<List<CommunityCommentsDTO>> response = new ApiResponse.Builder<List<CommunityCommentsDTO>>()
			.statusCode(SuccessCode.SELECT.getStatus())
			.message(SuccessCode.SELECT.getMessage())
			.result(comments)
			.build();
		return ResponseEntity.status(SuccessCode.SELECT.getStatus()).body(response);
	}

	// 댓글 수정
	@PutMapping("/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable("communityId") Integer communityId,
		@PathVariable("commentId") Integer commentId,
		@RequestBody @Valid CommunityCommentsDTO communityCommentsDTO,
		@RequestParam String email) {
		communityCommentsDTO.setCommunityPostId(communityId);
		CommunityCommentsDTO updatedComment = communityCommentsService.updateComment(commentId, communityCommentsDTO, email);

		if (updatedComment != null) {
			ApiResponse<CommunityCommentsDTO> response = new ApiResponse.Builder<CommunityCommentsDTO>()
				.statusCode(SuccessCode.UPDATE.getStatus())
				.message(SuccessCode.UPDATE.getMessage())
				.result(updatedComment)
				.build();
			return ResponseEntity.status(SuccessCode.UPDATE.getStatus()).body(response);
		} else {
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ErrorCode.UPDATE_ERROR.getMessage());
		}
	}

	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("communityId") Integer communityId,
		@PathVariable("commentId") Integer commentId,
		@RequestParam String email) {
		boolean isDeleted = communityCommentsService.deleteComment(commentId, email);

		if (isDeleted) {
			ApiResponse<String> response = new ApiResponse.Builder<String>()
				.statusCode(SuccessCode.DELETE.getStatus())
				.message(SuccessCode.DELETE.getMessage())
				.result("Comment deleted successfully")
				.build();
			return ResponseEntity.status(SuccessCode.DELETE.getStatus()).body(response);
		} else {
			return ResponseEntity.status(ErrorCode.DELETE_ERROR.getStatus())
				.body(ErrorCode.DELETE_ERROR.getMessage());
		}
	}

}
