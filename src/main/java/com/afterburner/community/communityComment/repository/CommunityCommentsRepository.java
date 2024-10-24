package com.afterburner.community.communityComment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.community.communityComment.model.CommunityComments;

@Repository
public interface CommunityCommentsRepository extends JpaRepository<CommunityComments, Integer> {
	// 특정 게시글(communityPostId)에 대한 모든 댓글 조회
	List<CommunityComments> findByCommunityPostId(Integer communityPostId);
}
