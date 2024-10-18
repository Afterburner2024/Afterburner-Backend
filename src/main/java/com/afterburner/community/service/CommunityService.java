package com.afterburner.community.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.community.model.Community;
import com.afterburner.community.model.CommunityDTO;
import com.afterburner.community.repository.CommunityRepository;

@Service
public class CommunityService {

	private final CommunityRepository communityRepository;

	@Autowired
	public CommunityService(CommunityRepository communityRepository) {
		this.communityRepository = communityRepository;
	}

	// 게시글 등록
	@Transactional
	public Community createPost(CommunityDTO communityDTO) {
		// null 체크
		if (communityDTO == null) {
			throw new IllegalArgumentException("요청 본문이 비어 있습니다.");
		}

		// DTO를 Entity로 변환
		Community community = new Community();
		community.setCommunityTitle(communityDTO.getCommunityTitle());
		community.setCommunityContent(communityDTO.getCommunityContent());
		community.setCommunityCreatedAt(communityDTO.getCommunityCreatedAt());
		community.setCommunityUserId(communityDTO.getCommunityUserId());
		community.setCommunityStatus(PostStatus.DEFAULT);
		community.setCommunityImg(communityDTO.getCommunityImg());

		// 게시글 저장
		return communityRepository.save(community);
	}

	// 전체 게시글 조회
	public List<CommunityDTO> getAllCommunities() {
		List<Community> communities = communityRepository.findAll();

		if (communities.isEmpty()) {
			throw new RuntimeException("게시글이 없습니다.");
		}

		// Entity 리스트를 DTO 리스트로 변환하여 반환
		return communities.stream()
			.map(community -> new CommunityDTO(
				community.getCommunityId(),
				community.getCommunityTitle(),
				community.getCommunityContent(),
				community.getCommunityCreatedAt(),
				community.getCommunityUpdatedAt(),
				community.getCommunityDeletedAt(),
				community.getCommunityStatus(),
				community.getCommunityUserId(),
				community.getCommunityImg()
			))
			.collect(Collectors.toList());
	}

	// 상세 조회 (communityId를 기준으로 조회)
	public CommunityDTO getCommunityById(Integer communityId) {
		// communityId에 맞는 게시글을 찾아 DTO로 변환 후 반환
		Optional<Community> communityOptional = communityRepository.findById(communityId);

		if (!communityOptional.isPresent()) {
			throw new RuntimeException("해당 ID의 게시글을 찾을 수 없습니다.");
		}

		Community community = communityOptional.get();

		return new CommunityDTO(
			community.getCommunityId(),
			community.getCommunityTitle(),
			community.getCommunityContent(),
			community.getCommunityCreatedAt(),
			community.getCommunityUpdatedAt(),
			community.getCommunityDeletedAt(),
			community.getCommunityStatus(),
			community.getCommunityUserId(),
			community.getCommunityImg()
		);
	}

	// 게시글 수정
	@Transactional
	public Community updatePost(Integer communityId, CommunityDTO communityDTO) {
		if (communityDTO == null) {
			throw new IllegalArgumentException("요청 본문이 비어 있습니다.");
		}

		Optional<Community> communityOptional = communityRepository.findById(communityId);

		if (!communityOptional.isPresent()) {
			throw new RuntimeException("해당 ID의 게시글을 찾을 수 없습니다.");
		}

		Community community = communityOptional.get();

		// 새로운 값으로 업데이트
		community.setCommunityTitle(communityDTO.getCommunityTitle());
		community.setCommunityContent(communityDTO.getCommunityContent());
		community.setCommunityStatus(communityDTO.getCommunityStatus());
		community.setCommunityImg(communityDTO.getCommunityImg());
		community.setCommunityUpdatedAt(LocalDateTime.now());

		// 수정된 게시글 저장
		return communityRepository.save(community);
	}

	// 게시글 삭제 (상태 변경)
	@Transactional
	public CommunityDTO deletePost(Integer communityId) {
		// 게시글 확인
		Community community = communityRepository.findById(communityId)
			.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

		// 상태를 DELETED로 변경
		community.setCommunityStatus(PostStatus.DELETED);
		community.setCommunityDeletedAt(LocalDateTime.now());

		// 상태가 변경된 게시글 저장
		Community deletedCommunity = communityRepository.save(community);

		return new CommunityDTO(
			deletedCommunity.getCommunityId(),
			deletedCommunity.getCommunityTitle(),
			deletedCommunity.getCommunityContent(),
			deletedCommunity.getCommunityCreatedAt(),
			deletedCommunity.getCommunityUpdatedAt(),
			deletedCommunity.getCommunityDeletedAt(),
			deletedCommunity.getCommunityStatus(),
			deletedCommunity.getCommunityUserId(),
			deletedCommunity.getCommunityImg()
		);
	}

}

