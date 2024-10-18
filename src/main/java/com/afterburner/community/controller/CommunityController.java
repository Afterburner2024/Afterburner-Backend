package com.afterburner.community.controller;

import java.time.LocalDateTime;
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
import com.afterburner.common.response.ApiResponse;
import com.afterburner.common.response.ErrorResponse;
import com.afterburner.community.model.Community;
import com.afterburner.community.model.CommunityDTO;
import com.afterburner.community.service.CommunityService;

@RestController
@RequestMapping("/api/v1/community")
public class CommunityController {

	private final CommunityService communityService;

	@Autowired
	public CommunityController(CommunityService communityService) {
		this.communityService = communityService;
	}

	// 이미지 등록 파일질라로 변경해야함.
	// 게시글 등록
	@PostMapping
	public ResponseEntity<?> createCommunityPost(@RequestBody CommunityDTO communityDTO) {
		if (communityDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		// 현재 시간으로 게시글 생성 시간 설정
		communityDTO.setCommunityCreatedAt(LocalDateTime.now());

		Community createdPost = communityService.createPost(communityDTO);

		if (createdPost != null) {
			ApiResponse<Community> response = new ApiResponse.Builder<Community>()
				.statusCode(SuccessCode.INSERT.getStatus())
				.message(SuccessCode.INSERT.getMessage())
				.result(createdPost)
				.build();
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(response);
		} else {
			return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
				.body(ErrorCode.INSERT_ERROR.getMessage());
		}
	}

	// 전체 게시글 조회
	@GetMapping
	public ResponseEntity<?> getAllCommunities() {
		List<CommunityDTO> communities = communityService.getAllCommunities();

		if (communities != null && !communities.isEmpty()) {
			ApiResponse<List<CommunityDTO>> response = new ApiResponse.Builder<List<CommunityDTO>>()
				.statusCode(SuccessCode.SELECT.getStatus())
				.message(SuccessCode.SELECT.getMessage())
				.result(communities)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ErrorCode.NOT_FOUND_ERROR.getMessage());
		}
	}

	// 상세조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getCommunityById(@PathVariable("id") Integer communityId) {
		CommunityDTO communityDTO = communityService.getCommunityById(communityId);

		if (communityDTO != null) {
			ApiResponse<CommunityDTO> response = new ApiResponse.Builder<CommunityDTO>()
				.statusCode(SuccessCode.SELECT.getStatus())
				.message(SuccessCode.SELECT.getMessage())
				.result(communityDTO)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ErrorCode.NOT_FOUND_ERROR.getMessage());
		}
	}

	// 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCommunityPost(@PathVariable("id") Integer communityId,
		@RequestBody CommunityDTO communityDTO) {
		Community updatedPost = communityService.updatePost(communityId, communityDTO);

		if (updatedPost != null) {
			ApiResponse<Community> response = new ApiResponse.Builder<Community>()
				.statusCode(SuccessCode.UPDATE.getStatus())
				.message(SuccessCode.UPDATE.getMessage())
				.result(updatedPost)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ErrorCode.UPDATE_ERROR.getMessage());
		}
	}

	// 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCommunity(@PathVariable("id") Integer communityId) {
		// 요청된 id 값이 null이거나 0 이하인 경우 유효하지 않은 요청 처리
		if (communityId == null || communityId <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}


		CommunityDTO deletedCommunity = communityService.deletePost(communityId);

		// 삭제 성공 시
		if (deletedCommunity != null) {
			ApiResponse<CommunityDTO> response = new ApiResponse.Builder<CommunityDTO>()
				.statusCode(SuccessCode.DELETE.getStatus())
				.message(SuccessCode.DELETE.getMessage())
				.result(deletedCommunity)  // 삭제된 게시글 정보를 응답으로 전달
				.build();
			return ResponseEntity.status(SuccessCode.DELETE.getStatus()).body(response);
		} else {
			// 삭제 실패 시
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	// 댓글

}
