package com.afterburner.community.communityComment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.community.communityComment.model.CommunityComments;
import com.afterburner.community.communityComment.model.CommunityCommentsDTO;
import com.afterburner.community.communityComment.repository.CommunityCommentsRepository;
import com.afterburner.community.model.Community;
import com.afterburner.community.repository.CommunityRepository;
import com.afterburner.oauth.model.User;
import com.afterburner.oauth.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommunityCommentsService {

	private final CommunityCommentsRepository communityCommentsRepository;
	private final CommunityRepository communityRepository;
	private final UserRepository userRepository;

	@Autowired
	public CommunityCommentsService(CommunityCommentsRepository communityCommentsRepository,
		CommunityRepository communityRepository,
		UserRepository userRepository) {
		this.communityCommentsRepository = communityCommentsRepository;
		this.communityRepository = communityRepository;
		this.userRepository = userRepository;
	}

	// 댓글 등록
	public CommunityCommentsDTO createComment(CommunityCommentsDTO commentDTO, String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

		Community community = communityRepository.findById(commentDTO.getCommunityPostId())
			.orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

		CommunityComments comment = new CommunityComments();
		comment.setCommunityCommentContent(commentDTO.getCommunityCommentContent());
		comment.setCommunityCommentStatus(PostStatus.DEFAULT);
		comment.setCommunityUserId(user.getId());
		comment.setCommunityPostId(commentDTO.getCommunityPostId());

		CommunityComments savedComment = communityCommentsRepository.save(comment);

		return new CommunityCommentsDTO(
			savedComment.getCommunityCommentId(),
			savedComment.getCommunityCommentContent(),
			savedComment.getCommunityCommentCreatedAt(),
			savedComment.getCommunityCommentUpdatedAt(),
			savedComment.getCommunityCommentDeletedAt(),
			savedComment.getCommunityCommentStatus(),
			savedComment.getCommunityUserId(),
			savedComment.getCommunityPostId()
		);
	}
	// 특정 게시글에 대한 댓글 목록 조회
	public List<CommunityCommentsDTO> getCommentsByPostId(Integer communityPostId) {
		List<CommunityComments> comments = communityCommentsRepository.findByCommunityPostId(communityPostId);
		return comments.stream().map(comment -> new CommunityCommentsDTO(
			comment.getCommunityCommentId(),
			comment.getCommunityCommentContent(),
			comment.getCommunityCommentCreatedAt(),
			comment.getCommunityCommentUpdatedAt(),
			comment.getCommunityCommentDeletedAt(),
			comment.getCommunityCommentStatus(),
			comment.getCommunityUserId(),
			comment.getCommunityPostId()
		)).collect(Collectors.toList());
	}
	// 댓글 수정
	public CommunityCommentsDTO updateComment(Integer commentId, CommunityCommentsDTO commentDTO, String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

		CommunityComments comment = communityCommentsRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

		if (!comment.getCommunityUserId().equals(user.getId())) {
			throw new SecurityException("댓글 작성자만 수정할 수 있습니다.");
		}

		comment.setCommunityCommentContent(commentDTO.getCommunityCommentContent());
		comment.setCommunityCommentUpdatedAt(LocalDateTime.now());

		CommunityComments updatedComment = communityCommentsRepository.save(comment);

		return new CommunityCommentsDTO(
			updatedComment.getCommunityCommentId(),
			updatedComment.getCommunityCommentContent(),
			updatedComment.getCommunityCommentCreatedAt(),
			updatedComment.getCommunityCommentUpdatedAt(),
			updatedComment.getCommunityCommentDeletedAt(),
			updatedComment.getCommunityCommentStatus(),
			updatedComment.getCommunityUserId(),
			updatedComment.getCommunityPostId()
		);
	}

	// 댓글 삭제
	public boolean deleteComment(Integer commentId, String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

		CommunityComments comment = communityCommentsRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

		if (!comment.getCommunityUserId().equals(user.getId())) {
			throw new SecurityException("댓글 작성자만 삭제할 수 있습니다.");
		}

		communityCommentsRepository.delete(comment);
		return true;
	}

}
