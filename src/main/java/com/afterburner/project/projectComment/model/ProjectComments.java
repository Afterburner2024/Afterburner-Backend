package com.afterburner.project.projectComment.model;

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
@Table(name = "project_comment")
public class ProjectComments {

	@Id
	@Column(name = "project_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectCommentId;

	@Column(name = "project_comment_content", columnDefinition = "VARCHAR(255)", nullable = false)
	private String projectCommentContent;

	@Column(name = "project_comment_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime projectCommentCreatedAt;

	@Column(name = "project_comment_updated_at")
	private LocalDateTime projectCommentUpdatedAt;

	@Column(name = "project_comment_deleted_at")
	private LocalDateTime projectCommentDeletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_comment_status", nullable = false)
	private PostStatus projectCommentStatus;

	@Column(name = "project_user_id", nullable = false)
	private Integer projectUserId;

	@Column(name = "project_post_id", nullable = false)
	private Integer projectPostId;

	public ProjectComments() {
	}

	public ProjectComments(Integer projectCommentId, String projectCommentContent,
		LocalDateTime projectCommentCreatedAt,
		LocalDateTime projectCommentUpdatedAt, LocalDateTime projectCommentDeletedAt, PostStatus projectCommentStatus,
		Integer projectUserId, Integer projectPostId) {
		this.projectCommentId = projectCommentId;
		this.projectCommentContent = projectCommentContent;
		this.projectCommentCreatedAt = projectCommentCreatedAt;
		this.projectCommentUpdatedAt = projectCommentUpdatedAt;
		this.projectCommentDeletedAt = projectCommentDeletedAt;
		this.projectCommentStatus = projectCommentStatus;
		this.projectUserId = projectUserId;
		this.projectPostId = projectPostId;
	}

	public Integer getProjectCommentId() {
		return projectCommentId;
	}

	public void setProjectCommentId(Integer projectCommentId) {
		this.projectCommentId = projectCommentId;
	}

	public String getProjectCommentContent() {
		return projectCommentContent;
	}

	public void setProjectCommentContent(String projectCommentContent) {
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

	public PostStatus getProjectCommentStatus() {
		return projectCommentStatus;
	}

	public void setProjectCommentStatus(PostStatus projectCommentStatus) {
		this.projectCommentStatus = projectCommentStatus;
	}

	public Integer getProjectUserId() {
		return projectUserId;
	}

	public void setProjectUserId(Integer projectUserId) {
		this.projectUserId = projectUserId;
	}

	public Integer getProjectPostId() {
		return projectPostId;
	}

	public void setProjectPostId(Integer projectPostId) {
		this.projectPostId = projectPostId;
	}

	@Override
	public String toString() {
		return "ProjectComments{" +
			"projectCommentId=" + projectCommentId +
			", projectCommentContent='" + projectCommentContent + '\'' +
			", projectCommentCreatedAt=" + projectCommentCreatedAt +
			", projectCommentUpdatedAt=" + projectCommentUpdatedAt +
			", projectCommentDeletedAt=" + projectCommentDeletedAt +
			", projectCommentStatus=" + projectCommentStatus +
			", projectUserId=" + projectUserId +
			", projectPostId=" + projectPostId +
			'}';
	}
}
