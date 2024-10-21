package com.afterburner.project.projectComment.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectCommentsDTO {

	private Integer projectCommentId;

	@NotEmpty(message = "댓글 내용은 필수입니다.")
	@Size(max = 100, message = "댓글은 100자 이내여야 합니다.")
	private String projectCommentContent;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectCommentCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectCommentUpdatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectCommentDeletedAt;

	@NotNull(message = "자유게시판 게시글 상태는 필수입니다.")
	private PostStatus projectCommentStatusc;

	@NotNull(message = "유저 id는 필수입니다.")
	private Integer projectUserId;

	@NotNull(message = "게시글 id는 필수입니다.")
	private Integer projectPostId;

	public ProjectCommentsDTO() {
	}

	public ProjectCommentsDTO(Integer projectCommentId, String projectCommentContent,
		LocalDateTime projectCommentCreatedAt,
		LocalDateTime projectCommentUpdatedAt, LocalDateTime projectCommentDeletedAt, PostStatus projectCommentStatusc,
		Integer projectUserId, Integer projectPostId) {
		this.projectCommentId = projectCommentId;
		this.projectCommentContent = projectCommentContent;
		this.projectCommentCreatedAt = projectCommentCreatedAt;
		this.projectCommentUpdatedAt = projectCommentUpdatedAt;
		this.projectCommentDeletedAt = projectCommentDeletedAt;
		this.projectCommentStatusc = projectCommentStatusc;
		this.projectUserId = projectUserId;
		this.projectPostId = projectPostId;
	}

	public Integer getProjectCommentId() {
		return projectCommentId;
	}

	public void setProjectCommentId(Integer projectCommentId) {
		this.projectCommentId = projectCommentId;
	}

	public @NotEmpty(message = "댓글 내용은 필수입니다.") @Size(max = 100, message = "댓글은 100자 이내여야 합니다.") String getProjectCommentContent() {
		return projectCommentContent;
	}

	public void setProjectCommentContent(
		@NotEmpty(message = "댓글 내용은 필수입니다.") @Size(max = 100, message = "댓글은 100자 이내여야 합니다.") String projectCommentContent) {
		this.projectCommentContent = projectCommentContent;
	}

	public LocalDateTime getProjectCommentCreatedAt() {
		return projectCommentCreatedAt;
	}

	public void setProjectCommentCreatedAt(LocalDateTime projectCommentCreatedAt) {
		this.projectCommentCreatedAt = projectCommentCreatedAt;
	}

	public LocalDateTime getProjectCommentUpdatedAt() {
		return projectCommentUpdatedAt;
	}

	public void setProjectCommentUpdatedAt(LocalDateTime projectCommentUpdatedAt) {
		this.projectCommentUpdatedAt = projectCommentUpdatedAt;
	}

	public LocalDateTime getProjectCommentDeletedAt() {
		return projectCommentDeletedAt;
	}

	public void setProjectCommentDeletedAt(LocalDateTime projectCommentDeletedAt) {
		this.projectCommentDeletedAt = projectCommentDeletedAt;
	}

	public @NotNull(message = "자유게시판 게시글 상태는 필수입니다.") PostStatus getProjectCommentStatusc() {
		return projectCommentStatusc;
	}

	public void setProjectCommentStatusc(
		@NotNull(message = "자유게시판 게시글 상태는 필수입니다.") PostStatus projectCommentStatusc) {
		this.projectCommentStatusc = projectCommentStatusc;
	}

	public @NotNull(message = "유저 id는 필수입니다.") Integer getProjectUserId() {
		return projectUserId;
	}

	public void setProjectUserId(
		@NotNull(message = "유저 id는 필수입니다.") Integer projectUserId) {
		this.projectUserId = projectUserId;
	}

	public @NotNull(message = "게시글 id는 필수입니다.") Integer getProjectPostId() {
		return projectPostId;
	}

	public void setProjectPostId(
		@NotNull(message = "게시글 id는 필수입니다.") Integer projectPostId) {
		this.projectPostId = projectPostId;
	}

	@Override
	public String toString() {
		return "ProjectCommentsDTO{" +
			"projectCommentId=" + projectCommentId +
			", projectCommentContent='" + projectCommentContent + '\'' +
			", projectCommentCreatedAt=" + projectCommentCreatedAt +
			", projectCommentUpdatedAt=" + projectCommentUpdatedAt +
			", projectCommentDeletedAt=" + projectCommentDeletedAt +
			", projectCommentStatusc=" + projectCommentStatusc +
			", projectUserId=" + projectUserId +
			", projectPostId=" + projectPostId +
			'}';
	}
}
