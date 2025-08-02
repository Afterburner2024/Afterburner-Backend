package com.afterburner.projectTeam.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.TeamMemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

	public ProjectTeamDTO(ProjectTeam teamMember) {
	}
}
