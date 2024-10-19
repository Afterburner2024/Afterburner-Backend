package com.afterburner.projectTeam.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.TeamMemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ProjectTeamDTO {

	private Integer projectTeamId;
	private Integer projectTeamPostId;
	private Integer projectTeamUserId;

	private TeamMemberRole projectTeamRole;

	private ProjectTeamPart projectTeamPart;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectTeamJoinedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime projectTeamQuitedAt;

	public ProjectTeamDTO() {
	}

	public ProjectTeamDTO(Integer projectTeamId, Integer projectTeamPostId, Integer projectTeamUserId,
		TeamMemberRole projectTeamRole, ProjectTeamPart projectTeamPart, LocalDateTime projectTeamJoinedAt,
		LocalDateTime projectTeamQuitedAt) {
		this.projectTeamId = projectTeamId;
		this.projectTeamPostId = projectTeamPostId;
		this.projectTeamUserId = projectTeamUserId;
		this.projectTeamRole = projectTeamRole;
		this.projectTeamPart = projectTeamPart;
		this.projectTeamJoinedAt = projectTeamJoinedAt;
		this.projectTeamQuitedAt = projectTeamQuitedAt;
	}

	public Integer getProjectTeamId() {
		return projectTeamId;
	}

	public void setProjectTeamId(Integer projectTeamId) {
		this.projectTeamId = projectTeamId;
	}

	public Integer getProjectTeamPostId() {
		return projectTeamPostId;
	}

	public void setProjectTeamPostId(Integer projectTeamPostId) {
		this.projectTeamPostId = projectTeamPostId;
	}

	public Integer getProjectTeamUserId() {
		return projectTeamUserId;
	}

	public void setProjectTeamUserId(Integer projectTeamUserId) {
		this.projectTeamUserId = projectTeamUserId;
	}

	public TeamMemberRole getProjectTeamRole() {
		return projectTeamRole;
	}

	public void setProjectTeamRole(TeamMemberRole projectTeamRole) {
		this.projectTeamRole = projectTeamRole;
	}

	public ProjectTeamPart getProjectTeamPart() {
		return projectTeamPart;
	}

	public void setProjectTeamPart(ProjectTeamPart projectTeamPart) {
		this.projectTeamPart = projectTeamPart;
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
			", projectTeamJoinedAt=" + projectTeamJoinedAt +
			", projectTeamQuitedAt=" + projectTeamQuitedAt +
			'}';
	}
}
