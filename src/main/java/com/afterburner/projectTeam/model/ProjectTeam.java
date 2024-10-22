package com.afterburner.projectTeam.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.common.enums.TeamMemberRole;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_team_part")
public class ProjectTeam {

	@Id
	@Column(name = "project_team_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectTeamId;

	@Column(name = "project_team_post_id", nullable = false)
	private Integer projectTeamPostId;

	@Column(name = "project_team_user_id", nullable = false)
	private Integer projectTeamUserId;

	@Column(name = "project_team_role", nullable = false)
	@Enumerated(EnumType.STRING)
	private TeamMemberRole projectTeamRole;

	@Column(name = "project_team_part")
	@Enumerated(EnumType.STRING)
	private ProjectTeamPart projectTeamPart;

	@Column(name = "project_team_member")
	@Enumerated(EnumType.STRING)
	private ProjectTeamMember projectTeamMember = ProjectTeamMember.PENDING; // 기본값 PENDING (신청 대기상태)

	@Column(name = "project_team_joined_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime projectTeamJoinedAt;

	@Column(name = "project_team_quited_at")
	private LocalDateTime projectTeamQuitedAt;

	public ProjectTeam() {
	}

	public ProjectTeam(Integer projectTeamId, Integer projectTeamPostId, Integer projectTeamUserId,
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

	public ProjectTeamMember getProjectTeamMember() {
		return projectTeamMember;
	}

	public void setProjectTeamMember(ProjectTeamMember projectTeamMember) {
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
		return "ProjectTeam{" +
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