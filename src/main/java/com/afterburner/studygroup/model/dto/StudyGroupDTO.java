package com.afterburner.studygroup.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.studygroup.model.entity.StudyRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyGroupDTO {

	private Integer studyGroupId;
	private List<String> studyGroupCategory;
	private String studyGroupTitle;

	// 한줄 소개
	private String studyGroupSummary;

	// 소개
	private String studyGroupContent;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime studyGroupCreatedAt;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime studyGroupUpdatedAt;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime studyGroupDeletedAt;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime studyGroupFinishedAt;

	private PostStatus studyGroupStatus;

	private List<String> studyGroupMembers;

	private Integer studyGroupUserId;

	@Enumerated(EnumType.STRING)
	private StudyRole studyGroupRole;

}
