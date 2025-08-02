package com.afterburner.project.model;

import java.time.LocalDateTime;
import java.util.List;

import com.afterburner.common.enums.PostStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

	private Integer projectId;

	@NotEmpty(message = "프로젝트 제목은 필수입니다.")
	@Size(max = 30, message = "프로젝트 제목은 30자 이내여야 합니다.")
	private String projectTitle;

	@NotEmpty(message = "프로젝트 요약은 필수입니다.")
	@Size(max= 20, message = "요약문은 20자 이내여야 합니다.")
	private String projectSummary;

	@NotEmpty(message = "프로젝트 내용은 필수입니다.")
	@Size(max = 5000, message = "내용은 5000자 이내여야 합니다.")
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

	private List<String> projectRecruitmentRoles;

        @NotNull(message = "사용자 ID는 필수입니다.")
        private Integer projectUserId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String projectUserName;

	private String projectRegion;

}
