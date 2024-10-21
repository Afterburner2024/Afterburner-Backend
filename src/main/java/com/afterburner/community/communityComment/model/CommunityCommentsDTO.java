package com.afterburner.community.communityComment.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommunityCommentsDTO {

	private Integer communityCommentId;

	@NotEmpty(message = "댓글 내용은 필수입니다.")
	@Size(max = 100, message = "댓글은 100자 이내여야 합니다.")
	private String communityCommentContent;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityCommentCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityCommentUpdatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityCommentDeletedAt;

	@NotNull(message = "자유게시판 게시글 상태는 필수입니다.")
	private PostStatus communityCommentStatusc;

	@NotNull(message = "유저 id는 필수입니다.")
	private Integer communityUserId;

	@NotNull(message = "게시글 id는 필수입니다.")
	private Integer communityPostId;

	public CommunityCommentsDTO() {
	}

	public CommunityCommentsDTO(Integer communityCommentId, String communityCommentContent,
		LocalDateTime communityCommentCreatedAt, LocalDateTime communityCommentUpdatedAt,
		LocalDateTime communityCommentDeletedAt, PostStatus communityCommentStatusc, Integer communityUserId,
		Integer communityPostId) {
		this.communityCommentId = communityCommentId;
		this.communityCommentContent = communityCommentContent;
		this.communityCommentCreatedAt = communityCommentCreatedAt;
		this.communityCommentUpdatedAt = communityCommentUpdatedAt;
		this.communityCommentDeletedAt = communityCommentDeletedAt;
		this.communityCommentStatusc = communityCommentStatusc;
		this.communityUserId = communityUserId;
		this.communityPostId = communityPostId;
	}

	public Integer getCommunityCommentId() {
		return communityCommentId;
	}

	public void setCommunityCommentId(Integer communityCommentId) {
		this.communityCommentId = communityCommentId;
	}

	public @NotEmpty(message = "댓글 내용은 필수입니다.") @Size(max = 100, message = "댓글은 100자 이내여야 합니다.") String getCommunityCommentContent() {
		return communityCommentContent;
	}

	public void setCommunityCommentContent(
		@NotEmpty(message = "댓글 내용은 필수입니다.") @Size(max = 100, message = "댓글은 100자 이내여야 합니다.") String communityCommentContent) {
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

	public @NotNull(message = "자유게시판 게시글 상태는 필수입니다.") PostStatus getCommunityCommentStatusc() {
		return communityCommentStatusc;
	}

	public void setCommunityCommentStatusc(
		@NotNull(message = "자유게시판 게시글 상태는 필수입니다.") PostStatus communityCommentStatusc) {
		this.communityCommentStatusc = communityCommentStatusc;
	}

	public @NotNull(message = "유저 id는 필수입니다.") Integer getCommunityUserId() {
		return communityUserId;
	}

	public void setCommunityUserId(
		@NotNull(message = "유저 id는 필수입니다.") Integer communityUserId) {
		this.communityUserId = communityUserId;
	}

	public @NotNull(message = "게시글 id는 필수입니다.") Integer getCommunityPostId() {
		return communityPostId;
	}

	public void setCommunityPostId(
		@NotNull(message = "게시글 id는 필수입니다.") Integer communityPostId) {
		this.communityPostId = communityPostId;
	}

	@Override
	public String toString() {
		return "CommunityCommentsDTO{" +
			"communityCommentId=" + communityCommentId +
			", communityCommentContent='" + communityCommentContent + '\'' +
			", communityCommentCreatedAt=" + communityCommentCreatedAt +
			", communityCommentUpdatedAt=" + communityCommentUpdatedAt +
			", communityCommentDeletedAt=" + communityCommentDeletedAt +
			", communityCommentStatusc=" + communityCommentStatusc +
			", communityUserId=" + communityUserId +
			", communityPostId=" + communityPostId +
			'}';
	}
}
