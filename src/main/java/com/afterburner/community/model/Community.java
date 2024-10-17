package com.afterburner.community.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.afterburner.common.enums.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Timestamp;

@Entity
@Table(name = "community")
public class Community {

	@Id
	@Column(name = "community_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer communityId;

	@Column(name = "community_title", columnDefinition = "VARCHAR(255)", nullable = false)
	private String communityTitle;

	@Column(name = "community_content", columnDefinition = "VARCHAR(3000)", nullable = false)
	private String communityContent;

	@Column(name = "community_created_at")
	@CreationTimestamp
	private LocalDateTime communityCreatedAt;

	@Column(name = "community_updated_at")
	private LocalDateTime communityUpdatedAt;

	@Column(name = "community_deleted_at")
	private LocalDateTime communityDeletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "community_status")
	private PostStatus communityStatus;

	@Column(name = "community_user_id", nullable = false)
	private Integer communityUserId;

	@Column(name = "community_img", columnDefinition = "VARCHAR(255)")
	private String communityImg;

	public Community() {
	}

	public Community(Integer communityId, String communityTitle, String communityContent,
		LocalDateTime communityCreatedAt,
		LocalDateTime communityUpdatedAt, LocalDateTime communityDeletedAt, PostStatus communityStatus,
		Integer communityUserId, String communityImg) {
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
		return "Community{" +
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
