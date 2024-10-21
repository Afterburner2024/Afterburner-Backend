package com.afterburner.community.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommunityDTO {

	private Integer communityId;

	@NotEmpty(message = "게시글 제목은 필수입니다.")
	@Size(max = 50, message = "게시글 제목은 50자 이내여야 합니다.")
	private String communityTitle;

	@NotEmpty(message = "게시글 내용은 필수입니다.")
	@Size(max = 3000, message = "게시글 내용은 3000자 이내여야 합니다.")
	private String communityContent;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityUpdatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime communityDeletedAt;

	@NotNull(message = "자유게시판 게시글 상태는 필수입니다.")
	private PostStatus communityStatus;

	@NotNull(message = "유저 id는 필수입니다.")
	private Integer communityUserId;

	private String communityImg;

	public CommunityDTO() {
	}

	public CommunityDTO(Integer communityId, String communityTitle, String communityContent,
		LocalDateTime communityCreatedAt, LocalDateTime communityUpdatedAt, LocalDateTime communityDeletedAt,
		PostStatus communityStatus, Integer communityUserId, String communityImg) {
		this.communityId = communityId;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityCreatedAt = communityCreatedAt;
		this.communityUpdatedAt = communityUpdatedAt;
		this.communityDeletedAt = communityDeletedAt;
		this.communityStatus = communityStatus;
		this.communityUserId = communityUserId;
		this.communityImg = communityImg;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getCommunityTitle() {
		return communityTitle;
	}

	public void setCommunityTitle(String communityTitle) {
		this.communityTitle = communityTitle;
	}

	public String getCommunityContent() {
		return communityContent;
	}

	public void setCommunityContent(String communityContent) {
		this.communityContent = communityContent;
	}

	public LocalDateTime getCommunityCreatedAt() {
		return communityCreatedAt;
	}

	public void setCommunityCreatedAt(LocalDateTime communityCreatedAt) {
		this.communityCreatedAt = communityCreatedAt;
	}

	public LocalDateTime getCommunityUpdatedAt() {
		return communityUpdatedAt;
	}

	public void setCommunityUpdatedAt(LocalDateTime communityUpdatedAt) {
		this.communityUpdatedAt = communityUpdatedAt;
	}

	public LocalDateTime getCommunityDeletedAt() {
		return communityDeletedAt;
	}

	public void setCommunityDeletedAt(LocalDateTime communityDeletedAt) {
		this.communityDeletedAt = communityDeletedAt;
	}

	public PostStatus getCommunityStatus() {
		return communityStatus;
	}

	public void setCommunityStatus(PostStatus communityStatus) {
		this.communityStatus = communityStatus;
	}

	public Integer getCommunityUserId() {
		return communityUserId;
	}

	public void setCommunityUserId(Integer communityUserId) {
		this.communityUserId = communityUserId;
	}

	public String getCommunityImg() {
		return communityImg;
	}

	public void setCommunityImg(String communityImg) {
		this.communityImg = communityImg;
	}

	@Override
	public String toString() {
		return "CommunityDTO{" +
			"communityId=" + communityId +
			", communityTitle='" + communityTitle + '\'' +
			", communityContent='" + communityContent + '\'' +
			", communityCreatedAt=" + communityCreatedAt +
			", communityUpdatedAt=" + communityUpdatedAt +
			", communityDeletedAt=" + communityDeletedAt +
			", communityStatus=" + communityStatus +
			", communityUserId=" + communityUserId +
			", communityImg='" + communityImg + '\'' +
			'}';
	}
}
