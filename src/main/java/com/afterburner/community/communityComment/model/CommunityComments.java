package com.afterburner.community.communityComment.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.afterburner.common.enums.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "community_comment")
public class CommunityComments {

	@Id
	@Column(name = "community_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer communityCommentId;

	@Column(name = "community_comment_content", columnDefinition = "VARCHAR(255)", nullable = false)
	private String communityCommentContent;

	@Column(name = "community_comment_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime communityCommentCreatedAt;

	@Column(name = "community_comment_updated_at")
	private LocalDateTime communityCommentUpdatedAt;

	@Column(name = "community_comment_deleted_at")
	private LocalDateTime communityCommentDeletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "community_comment_status", nullable = false)
	private PostStatus communityCommentStatus;

	@Column(name = "community_user_id", nullable = false)
	private Integer communityUserId;

	@Column(name = "community_post_id", nullable = false)
	private Integer communityPostId;

	public CommunityComments() {
	}

	public CommunityComments(Integer communityCommentId, String communityCommentContent,
		LocalDateTime communityCommentCreatedAt, LocalDateTime communityCommentUpdatedAt,
		LocalDateTime communityCommentDeletedAt, PostStatus communityCommentStatus, Integer communityUserId,
		Integer communityPostId) {
		this.communityCommentId = communityCommentId;
		this.communityCommentContent = communityCommentContent;
		this.communityCommentCreatedAt = communityCommentCreatedAt;
		this.communityCommentUpdatedAt = communityCommentUpdatedAt;
		this.communityCommentDeletedAt = communityCommentDeletedAt;
		this.communityCommentStatus = communityCommentStatus;
		this.communityUserId = communityUserId;
		this.communityPostId = communityPostId;
	}

	public Integer getCommunityCommentId() {
		return communityCommentId;
	}

	public void setCommunityCommentId(Integer communityCommentId) {
		this.communityCommentId = communityCommentId;
	}

	public String getCommunityCommentContent() {
		return communityCommentContent;
	}

	public void setCommunityCommentContent(String communityCommentContent) {
		this.communityCommentContent = communityCommentContent;
	}

	public LocalDateTime getCommunityCommentCreatedAt() {
		return communityCommentCreatedAt;
	}

	public void setCommunityCommentCreatedAt(LocalDateTime communityCommentCreatedAt) {
		this.communityCommentCreatedAt = communityCommentCreatedAt;
	}

	public LocalDateTime getCommunityCommentUpdatedAt() {
		return communityCommentUpdatedAt;
	}

	public void setCommunityCommentUpdatedAt(LocalDateTime communityCommentUpdatedAt) {
		this.communityCommentUpdatedAt = communityCommentUpdatedAt;
	}

	public LocalDateTime getCommunityCommentDeletedAt() {
		return communityCommentDeletedAt;
	}

	public void setCommunityCommentDeletedAt(LocalDateTime communityCommentDeletedAt) {
		this.communityCommentDeletedAt = communityCommentDeletedAt;
	}

	public PostStatus getCommunityCommentStatus() {
		return communityCommentStatus;
	}

	public void setCommunityCommentStatus(PostStatus communityCommentStatus) {
		this.communityCommentStatus = communityCommentStatus;
	}

	public Integer getCommunityUserId() {
		return communityUserId;
	}

	public void setCommunityUserId(Integer communityUserId) {
		this.communityUserId = communityUserId;
	}

	public Integer getCommunityPostId() {
		return communityPostId;
	}

	public void setCommunityPostId(Integer communityPostId) {
		this.communityPostId = communityPostId;
	}

	@Override
	public String toString() {
		return "CommunityComments{" +
			"communityCommentId=" + communityCommentId +
			", communityCommentContent='" + communityCommentContent + '\'' +
			", communityCommentCreatedAt=" + communityCommentCreatedAt +
			", communityCommentUpdatedAt=" + communityCommentUpdatedAt +
			", communityCommentDeletedAt=" + communityCommentDeletedAt +
			", communityCommentStatus=" + communityCommentStatus +
			", communityUserId=" + communityUserId +
			", communityPostId=" + communityPostId +
			'}';
	}
}
