package com.afterburner.project.model;

import java.time.LocalDateTime;
import java.util.List;

import com.afterburner.common.enums.PostStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectDTO {

	private Integer projectId;

	@NotEmpty(message = "프로젝트 제목은 필수입니다.")
	@Size(max = 100, message = "프로젝트 제목은 100자 이내여야 합니다.")
	private String projectTitle;

	@NotEmpty(message = "프로젝트 내용은 필수입니다.")
	private String projectContent;

	private String projectLink;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectUpdatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectDeletedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectFinishedAt;

	@NotNull(message = "프로젝트 상태는 필수입니다.")
	private PostStatus projectStatus;

	@NotNull(message = "프로젝트 기술 스택은 필수입니다.")
	private List<String> projectTechStack;

	@NotNull(message = "사용자 ID는 필수입니다.") // null이 아니어야 함
	private Integer projectUserId;

	public ProjectDTO() {
	}

	public ProjectDTO(Integer projectId, String projectTitle, String projectContent, String projectLink,
		LocalDateTime projectCreatedAt, LocalDateTime projectUpdatedAt, LocalDateTime projectDeletedAt,
		LocalDateTime projectFinishedAt, PostStatus projectStatus, List<String> projectTechStack, Integer projectUserId) {
		this.projectId = projectId;
		this.projectTitle = projectTitle;
		this.projectContent = projectContent;
		this.projectLink = projectLink;
		this.projectCreatedAt = projectCreatedAt;
		this.projectUpdatedAt = projectUpdatedAt;
		this.projectDeletedAt = projectDeletedAt;
		this.projectFinishedAt = projectFinishedAt;
		this.projectStatus = projectStatus;
		this.projectTechStack = projectTechStack;
		this.projectUserId = projectUserId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	public String getProjectLink() {
		return projectLink;
	}

	public void setProjectLink(String projectLink) {
		this.projectLink = projectLink;
	}


	public LocalDateTime getProjectCreatedAt() {
		return projectCreatedAt;
	}


	public LocalDateTime getProjectUpdatedAt() {
		return projectUpdatedAt;
	}

	public void setProjectUpdatedAt(LocalDateTime projectUpdatedAt) {
		this.projectUpdatedAt = projectUpdatedAt;
	}


	public LocalDateTime getProjectDeletedAt() {
		return projectDeletedAt;
	}

	public void setProjectDeletedAt(LocalDateTime projectDeletedAt) {
		this.projectDeletedAt = projectDeletedAt;
	}

	public LocalDateTime getProjectFinishedAt() {
		return projectFinishedAt;
	}

	public void setProjectFinishedAt(LocalDateTime projectFinishedAt) {
		this.projectFinishedAt = projectFinishedAt;
	}

	public PostStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(PostStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public List<String> getProjectTechStack() {
		return projectTechStack;
	}

	public void setProjectTechStack(List<String> projectTechStack) {
		this.projectTechStack = projectTechStack;
	}

	public Integer getProjectUserId() {
		return projectUserId;
	}

	public void setProjectUserId(Integer projectUserId) {
		this.projectUserId = projectUserId;
	}

	@Override
	public String toString() {
		return "ProjectDTO{" +
			"projectId=" + projectId +
			", projectTitle='" + projectTitle + '\'' +
			", projectContent='" + projectContent + '\'' +
			", projectLink='" + projectLink + '\'' +
			", projectCreatedAt=" + projectCreatedAt +
			", projectUpdatedAt=" + projectUpdatedAt +
			", projectDeletedAt=" + projectDeletedAt +
			", projectFinishedAt=" + projectFinishedAt +
			", projectStatus=" + projectStatus +
			", projectTechStack='" + projectTechStack + '\'' +
			", projectUserId=" + projectUserId +
			'}';
	}
}
