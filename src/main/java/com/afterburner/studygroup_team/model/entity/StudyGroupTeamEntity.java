package com.afterburner.studygroup_team.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.afterburner.common.enums.TeamMemberRole;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "studygroup_team")
public class StudyGroupTeamEntity {

	@Id
	@Column(name = "studygroup_team_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studyGroupTeamId;

	@ManyToOne
	@JoinColumn(name = "studygroup_team_post_id", referencedColumnName = "studygroup_id", nullable = false)
	private StudyGroupEntity studyGroupTeamPostId;

	@Column(name = "studygroup_team_user_id")
	private Integer studyGroupTeamUserId;

	@Column(name = "studygroup_team_role", nullable = false)
	private TeamMemberRole studyGroupTeamRole;

	@Column(name = "studygroup_team_joined_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime studyGroupJoinedAt;

	@Column(name = "studygroup_team_quited_at")
	private LocalDateTime studyGroupQuitedAt;

	public StudyGroupTeamEntity() {
	}

	public StudyGroupTeamEntity(Integer studyGroupTeamId, StudyGroupEntity studyGroupTeamPostId,
		Integer studyGroupTeamUserId, TeamMemberRole studyGroupTeamRole, LocalDateTime studyGroupJoinedAt,
		LocalDateTime studyGroupQuitedAt) {
		this.studyGroupTeamId = studyGroupTeamId;
		this.studyGroupTeamPostId = studyGroupTeamPostId;
		this.studyGroupTeamUserId = studyGroupTeamUserId;
		this.studyGroupTeamRole = studyGroupTeamRole;
		this.studyGroupJoinedAt = studyGroupJoinedAt;
		this.studyGroupQuitedAt = studyGroupQuitedAt;
	}

	public Integer getStudyGroupTeamId() {
		return studyGroupTeamId;
	}

	public void setStudyGroupTeamId(Integer studyGroupTeamId) {
		this.studyGroupTeamId = studyGroupTeamId;
	}

	public StudyGroupEntity getStudyGroupTeamPostId() {
		return studyGroupTeamPostId;
	}

	public void setStudyGroupTeamPostId(StudyGroupEntity studyGroupTeamPostId) {
		this.studyGroupTeamPostId = studyGroupTeamPostId;
	}

	public Integer getStudyGroupTeamUserId() {
		return studyGroupTeamUserId;
	}

	public void setStudyGroupTeamUserId(Integer studyGroupTeamUserId) {
		this.studyGroupTeamUserId = studyGroupTeamUserId;
	}

	public TeamMemberRole getStudyGroupTeamRole() {
		return studyGroupTeamRole;
	}

	public void setStudyGroupTeamRole(TeamMemberRole studyGroupTeamRole) {
		this.studyGroupTeamRole = studyGroupTeamRole;
	}

	public LocalDateTime getStudyGroupJoinedAt() {
		return studyGroupJoinedAt;
	}

	public void setStudyGroupJoinedAt(LocalDateTime studyGroupJoinedAt) {
		this.studyGroupJoinedAt = studyGroupJoinedAt;
	}

	public LocalDateTime getStudyGroupQuitedAt() {
		return studyGroupQuitedAt;
	}

	public void setStudyGroupQuitedAt(LocalDateTime studyGroupQuitedAt) {
		this.studyGroupQuitedAt = studyGroupQuitedAt;
	}

	@Override
	public String toString() {
		return "StudyGroupTeamEntity{" +
			"studyGroupTeamId=" + studyGroupTeamId +
			", studyGroupTeamPostId=" + studyGroupTeamPostId +
			", studyGroupTeamUserId=" + studyGroupTeamUserId +
			", studyGroupTeamRole=" + studyGroupTeamRole +
			", studyGroupJoinedAt=" + studyGroupJoinedAt +
			", studyGroupQuitedAt=" + studyGroupQuitedAt +
			'}';
	}
}
