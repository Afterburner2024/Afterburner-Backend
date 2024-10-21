package com.afterburner.projectTeam.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.TeamMemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class ProjectTeamDTO {

	@NotNull(message = "프로젝트팀원 id는 필수입니다.")
	private Integer projectTeamId;

	@NotNull(message = "프로젝트 게시글 id는 필수입니다.")
	private Integer projectTeamPostId;

	@NotNull(message = "프로젝트를 신청한 유저의 ID는 필수입니다.")
	private Integer projectTeamUserId;

	@NotNull(message = "팀원의 역할은 필수입니다.")
	private TeamMemberRole projectTeamRole;

	@NotNull(message = "팀원의 담당 역할은 필수입니다.")
	private ProjectTeamPart projectTeamPart;

	@NotNull(message = "팀원의 신청 상태는 필수입니다.")
	private ProjectTeamMember projectTeamMember;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectTeamJoinedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectTeamQuitedAt;

	public ProjectTeamDTO() {
	}

	public ProjectTeamDTO(Integer projectTeamId, Integer projectTeamPostId, Integer projectTeamUserId,
		TeamMemberRole projectTeamRole, ProjectTeamPart projectTeamPart, ProjectTeamMember projectTeamMember,
		LocalDateTime projectTeamJoinedAt, LocalDateTime projectTeamQuitedAt) {
		this.projectTeamId = projectTeamId;
		this.projectTeamPostId = projectTeamPostId;
		this.projectTeamUserId = projectTeamUserId;
		this.projectTeamRole = projectTeamRole;
		this.projectTeamPart = projectTeamPart;
		this.projectTeamMember = projectTeamMember;
		this.projectTeamJoinedAt = projectTeamJoinedAt;
		this.projectTeamQuitedAt = projectTeamQuitedAt;
	}

	public ProjectTeamDTO(ProjectTeam teamMember) {
	}

	public @NotNull(message = "프로젝트팀원 id는 필수입니다.") Integer getProjectTeamId() {
		return projectTeamId;
	}

	public void setProjectTeamId(
		@NotNull(message = "프로젝트팀원 id는 필수입니다.") Integer projectTeamId) {
		this.projectTeamId = projectTeamId;
	}

	public @NotNull(message = "프로젝트 게시글 id는 필수입니다.") Integer getProjectTeamPostId() {
		return projectTeamPostId;
	}

	public void setProjectTeamPostId(
		@NotNull(message = "프로젝트 게시글 id는 필수입니다.") Integer projectTeamPostId) {
		this.projectTeamPostId = projectTeamPostId;
	}

	public @NotNull(message = "프로젝트를 신청한 유저의 ID는 필수입니다.") Integer getProjectTeamUserId() {
		return projectTeamUserId;
	}

	public void setProjectTeamUserId(
		@NotNull(message = "프로젝트를 신청한 유저의 ID는 필수입니다.") Integer projectTeamUserId) {
		this.projectTeamUserId = projectTeamUserId;
	}

	public @NotNull(message = "팀원의 역할은 필수입니다.") TeamMemberRole getProjectTeamRole() {
		return projectTeamRole;
	}

	public void setProjectTeamRole(
		@NotNull(message = "팀원의 역할은 필수입니다.") TeamMemberRole projectTeamRole) {
		this.projectTeamRole = projectTeamRole;
	}

	public @NotNull(message = "팀원의 담당 역할은 필수입니다.") ProjectTeamPart getProjectTeamPart() {
		return projectTeamPart;
	}

	public void setProjectTeamPart(
		@NotNull(message = "팀원의 담당 역할은 필수입니다.") ProjectTeamPart projectTeamPart) {
		this.projectTeamPart = projectTeamPart;
	}

	public @NotNull(message = "팀원의 신청 상태는 필수입니다.") ProjectTeamMember getProjectTeamMember() {
		return projectTeamMember;
	}

	public void setProjectTeamMember(
		@NotNull(message = "팀원의 신청 상태는 필수입니다.") ProjectTeamMember projectTeamMember) {
		this.projectTeamMember = projectTeamMember;
	}

	public LocalDateTime getProjectTeamJoinedAt() {
		return projectTeamJoinedAt;
	}

	public void setProjectTeamJoinedAt(LocalDateTime projectTeamJoinedAt) {
		this.projectTeamJoinedAt = projectTeamJoinedAt;
	}

	public LocalDateTime getProjectTeamQuitedAt() {
		return projectTeamQuitedAt;
	}

	public void setProjectTeamQuitedAt(LocalDateTime projectTeamQuitedAt) {
		this.projectTeamQuitedAt = projectTeamQuitedAt;
	}

	@Override
	public String toString() {
		return "ProjectTeamDTO{" +
			"projectTeamId=" + projectTeamId +
			", projectTeamPostId=" + projectTeamPostId +
			", projectTeamUserId=" + projectTeamUserId +
			", projectTeamRole=" + projectTeamRole +
			", projectTeamPart=" + projectTeamPart +
			", projectTeamMember=" + projectTeamMember +
			", projectTeamJoinedAt=" + projectTeamJoinedAt +
			", projectTeamQuitedAt=" + projectTeamQuitedAt +
			'}';
	}
}
