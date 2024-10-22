package com.afterburner.project.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import com.afterburner.common.enums.PostStatus;
import com.afterburner.projectTeam.model.ProjectTeamPart;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@Column(name = "project_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;

	@Column(name = "project_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String projectTitle;

	@Column(name = "project_content", nullable = false, columnDefinition = "VARCHAR(5000)")
	private String projectContent;

	@Column(name = "project_link", columnDefinition = "VARCHAR(255)")
	private String projectLink;

	@Column(name = "project_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime projectCreatedAt;

	@Column(name = "project_updated_at")
	private LocalDateTime projectUpdatedAt;

	@Column(name = "project_deleted_at")
	private LocalDateTime projectDeletedAt;

	@Column(name = "project_finished_at")
	private LocalDateTime projectFinishedAt;

	@Column(name = "project_status", nullable = false)
	private PostStatus projectStatus;

	@Column(name = "project_tech_stack", columnDefinition = "jsonb")
	@Type(JsonBinaryType.class)
	private List<String> projectTechStack;

	@Type(JsonBinaryType.class)
	@Column(name = "project_team_part_limits", columnDefinition = "jsonb")
	private Map<ProjectTeamPart, Integer> teamPartLimits;

	@Column(name = "project_user_id", nullable = false)
	private Integer projectUserId;

	public Project() {
		this.teamPartLimits = new HashMap<>();
	}

	// 빌더 패턴 생성자
	private Project(Builder builder) {
		this.projectTitle = builder.projectTitle;
		this.projectContent = builder.projectContent;
		this.projectLink = builder.projectLink;
		this.projectStatus = builder.projectStatus;
		this.projectUserId = builder.projectUserId;
		this.projectTechStack = builder.projectTechStack;
		this.teamPartLimits = builder.teamPartLimits;
	}

	public static class Builder {
		private String projectTitle;
		private String projectContent;
		private String projectLink;
		private PostStatus projectStatus;
		private List<String> projectTechStack;
		private Integer projectUserId;
		private Map<ProjectTeamPart, Integer> teamPartLimits = new HashMap<>();

		public Builder projectTitle(String projectTitle) {
			if (projectTitle.length() > 30) {
				throw new IllegalArgumentException("제목은 30자 미만만 가능합니다.");
			}
			this.projectTitle = projectTitle;
			return this;
		}

		public Builder projectContent(String projectContent) {
			if (projectContent.length() > 5000) {
				throw new IllegalArgumentException("내용은 5000자 미만만 가능합니다.");
			}
			this.projectContent = projectContent;
			return this;
		}

		public Builder projectLink(String projectLink) {
			this.projectLink = projectLink;
			return this;
		}

		public Builder projectStatus(PostStatus projectStatus) {
			this.projectStatus = projectStatus;
			return this;
		}

		public Builder projectTechStack(List<String> projectTechStack) {
			this.projectTechStack = projectTechStack;
			return this;
		}

		public Builder projectUserId(Integer projectUserId) {
			this.projectUserId = projectUserId;
			return this;
		}

		public Builder teamPartLimits(Map<ProjectTeamPart, Integer> teamPartLimits) {
			this.teamPartLimits = teamPartLimits;
			return this;
		}

		public Project build() {
			if (projectTitle == null || projectContent == null || projectStatus == null || projectUserId == null) {
				throw new IllegalArgumentException("모든 필드를 채워주세요.");
			}
			return new Project(this);
		}
	}

	@PrePersist // 처음 저장될 때 호출됨
	public void prePersist() {
		this.projectUpdatedAt = null;
	}

	@PreUpdate // 수정될 때 호출됨
	public void preUpdate() {
		this.projectUpdatedAt = LocalDateTime.now();
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

	public void setProjectCreatedAt(LocalDateTime projectCreatedAt) {
		this.projectCreatedAt = projectCreatedAt;
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

	public Map<ProjectTeamPart, Integer> getTeamPartLimits() {
		return teamPartLimits;
	}

	public void setTeamPartLimits(Map<ProjectTeamPart, Integer> teamPartLimits) {
		this.teamPartLimits = teamPartLimits;
	}

	public Integer getProjectUserId() {
		return projectUserId;
	}

	public void setProjectUserId(Integer projectUserId) {
		this.projectUserId = projectUserId;
	}

	@Override
	public String toString() {
		return "Project{" +
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
			", teamPartLimits='" + teamPartLimits + '\'' +
			", projectUserId=" + projectUserId +
			'}';
	}
}
