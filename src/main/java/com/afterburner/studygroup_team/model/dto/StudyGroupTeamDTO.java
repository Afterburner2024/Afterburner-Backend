package com.afterburner.studygroup_team.model.dto;

import java.time.LocalDateTime;

import com.afterburner.common.enums.TeamMemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;

public class StudyGroupTeamDTO {

	private Integer studyGroupTeamId;
	private Integer studyGroupTeamPostId;
	private Integer studyGroupTeamUserId;
	private TeamMemberRole studyGroupTeamRole;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime studyGroupJoinedAt; // 화면에서 각 팀원들의 가입일을 보여주기 위해 넣음

	public StudyGroupTeamDTO() {
	}

	public StudyGroupTeamDTO(Integer studyGroupTeamId, Integer studyGroupTeamPostId, Integer studyGroupTeamUserId,
		TeamMemberRole studyGroupTeamRole, LocalDateTime studyGroupJoinedAt) {
		this.studyGroupTeamId = studyGroupTeamId;
		this.studyGroupTeamPostId = studyGroupTeamPostId;
		this.studyGroupTeamUserId = studyGroupTeamUserId;
		this.studyGroupTeamRole = studyGroupTeamRole;
		this.studyGroupJoinedAt = studyGroupJoinedAt;
	}

	public Integer getStudyGroupTeamId() {
		return studyGroupTeamId;
	}

	public void setStudyGroupTeamId(Integer studyGroupTeamId) {
		this.studyGroupTeamId = studyGroupTeamId;
	}

	public Integer getStudyGroupTeamPostId() {
		return studyGroupTeamPostId;
	}

	public void setStudyGroupTeamPostId(Integer studyGroupTeamPostId) {
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

	@Override
	public String toString() {
		return "StudyGroupTeamDTO{" +
			"studyGroupTeamId=" + studyGroupTeamId +
			", studyGroupTeamPostId=" + studyGroupTeamPostId +
			", studyGroupTeamUserId=" + studyGroupTeamUserId +
			", studyGroupTeamRole=" + studyGroupTeamRole +
			", studyGroupJoinedAt=" + studyGroupJoinedAt +
			'}';
	}
}
