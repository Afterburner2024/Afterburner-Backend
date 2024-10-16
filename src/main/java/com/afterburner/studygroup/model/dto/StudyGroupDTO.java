package com.afterburner.studygroup.model.dto;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.studygroup.model.entity.StudyGroupCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudyGroupDTO {

	private Integer studyGroupId;
	private StudyGroupCategory studyGroupCategory;
	private String studyGroupTitle;
	private String studyGroupContent;
	private LocalDateTime studyGroupCreatedAt;
	private LocalDateTime studyGroupUpdatedAt;
	private PostStatus studyGroupStatus;
	private Integer studyGroupUserId;

	public StudyGroupDTO() {
	}

	public StudyGroupDTO(Integer studyGroupId, StudyGroupCategory studyGroupCategory, String studyGroupTitle,
		String studyGroupContent, LocalDateTime studyGroupCreatedAt, LocalDateTime studyGroupUpdatedAt,
		PostStatus studyGroupStatus, Integer studyGroupUserId) {
		this.studyGroupId = studyGroupId;
		this.studyGroupCategory = studyGroupCategory;
		this.studyGroupTitle = studyGroupTitle;
		this.studyGroupContent = studyGroupContent;
		this.studyGroupCreatedAt = studyGroupCreatedAt;
		this.studyGroupUpdatedAt = studyGroupUpdatedAt;
		this.studyGroupStatus = studyGroupStatus;
		this.studyGroupUserId = studyGroupUserId;
	}

	public Integer getStudyGroupId() {
		return studyGroupId;
	}

	public void setStudyGroupId(Integer studyGroupId) {
		this.studyGroupId = studyGroupId;
	}

	public StudyGroupCategory getStudyGroupCategory() {
		return studyGroupCategory;
	}

	public void setStudyGroupCategory(StudyGroupCategory studyGroupCategory) {
		this.studyGroupCategory = studyGroupCategory;
	}

	public String getStudyGroupTitle() {
		return studyGroupTitle;
	}

	public void setStudyGroupTitle(String studyGroupTitle) {
		this.studyGroupTitle = studyGroupTitle;
	}

	public String getStudyGroupContent() {
		return studyGroupContent;
	}

	public void setStudyGroupContent(String studyGroupContent) {
		this.studyGroupContent = studyGroupContent;
	}

	public LocalDateTime getStudyGroupCreatedAt() {
		return studyGroupCreatedAt;
	}

	public void setStudyGroupCreatedAt(LocalDateTime studyGroupCreatedAt) {
		this.studyGroupCreatedAt = studyGroupCreatedAt;
	}

	public LocalDateTime getStudyGroupUpdatedAt() {
		return studyGroupUpdatedAt;
	}

	public void setStudyGroupUpdatedAt(LocalDateTime studyGroupUpdatedAt) {
		this.studyGroupUpdatedAt = studyGroupUpdatedAt;
	}

	public PostStatus getStudyGroupStatus() {
		return studyGroupStatus;
	}

	public void setStudyGroupStatus(PostStatus studyGroupStatus) {
		this.studyGroupStatus = studyGroupStatus;
	}

	public Integer getStudyGroupUserId() {
		return studyGroupUserId;
	}

	public void setStudyGroupUserId(Integer studyGroupUserId) {
		this.studyGroupUserId = studyGroupUserId;
	}

	@Override
	public String toString() {
		return "StudyGroupDTO{" +
			"studyGroupId=" + studyGroupId +
			", studyGroupCategory=" + studyGroupCategory +
			", studyGroupTitle='" + studyGroupTitle + '\'' +
			", studyGroupContent='" + studyGroupContent + '\'' +
			", studyGroupCreatedAt=" + studyGroupCreatedAt +
			", studyGroupUpdatedAt=" + studyGroupUpdatedAt +
			", studyGroupStatus=" + studyGroupStatus +
			", studyGroupUserId=" + studyGroupUserId +
			'}';
	}
}
