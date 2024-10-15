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

	// 게시글 등록
	@PostMapping("/post")
	public ResponseEntity<Community> createCommunityPost(@RequestBody CommunityDTO communityDTO) {

		// 현재 시간으로 게시글 생성 시간 설정
		communityDTO.setCommunityCreatedAt(LocalDateTime.now());

		Community createdPost = communityService.createPost(communityDTO);

		return ResponseEntity.ok(createdPost);
	}

	// 전체 게시글 조회
	@GetMapping()
	public ResponseEntity<List<CommunityDTO>> getAllCommunities() {
		List<CommunityDTO> communities = communityService.getAllCommunities();
		return ResponseEntity.ok(communities);
	}

	// 상세조회
	@GetMapping("/{id}")
	public ResponseEntity<CommunityDTO> getCommunityById(@PathVariable("id") Integer communityId) {
		CommunityDTO communityDTO = communityService.getCommunityById(communityId);
		return ResponseEntity.ok(communityDTO);
	}

	// 수정
	@PutMapping("/{id}")
	public ResponseEntity<Community> updateCommunityPost(@PathVariable("id") Integer communityId,
		@RequestBody CommunityDTO communityDTO) {
		Community updatedPost = communityService.updatePost(communityId, communityDTO);
		return ResponseEntity.ok(updatedPost);
	}

	// 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCommunity(@PathVariable("id") Integer communityId) {
		communityService.deletePost(communityId);
		return ResponseEntity.noContent().build();
	}

	// 댓글

}
