package com.afterburner.studygroup.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.afterburner.common.enums.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "studygroup")
public class StudyGroupEntity {

	@Id
	@Column(name = "studygroup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studyGroupId;

	@Column(name = "studygroup_category", nullable = false)
	private StudyGroupCategory studyGroupCategory;

	@Column(name = "studygroup_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String studyGroupTitle;

	@Column(name = "studygroup_content", nullable = false, columnDefinition = "VARCHAR(5000)")
	private String studyGroupContent;

	@Column(name = "studygroup_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime studyGroupCreatedAt;

	@Column(name = "studygroup_updated_at")
	private LocalDateTime studyGroupUpdatedAt;

	@Column(name = "studygroup_deleted_at")
	private LocalDateTime studyGroupDeletedAt;

	@Column(name = "studygroup_status", nullable = false) // 기본값, 삭제상태, 신고된 상태
	private PostStatus studyGroupStatus;

	@Column(name = "studygroup_user_id", nullable = false) // 작성자 ID
	private Integer studyGroupUserId;

	public StudyGroupEntity() {
	}

	public StudyGroupEntity(Builder builder) {
		this.studyGroupCategory = builder.studyGroupCategory;
		this.studyGroupTitle = builder.studyGroupTitle;
		this.studyGroupContent = builder.studyGroupContent;
		this.studyGroupStatus = builder.studyGroupStatus;
		this.studyGroupUserId = builder.studyGroupUserId;
	}

	public static class Builder{
		private StudyGroupCategory studyGroupCategory;
		private String studyGroupTitle;
		private String studyGroupContent;
		private PostStatus studyGroupStatus;
		private Integer studyGroupUserId;

		public Builder studyGroupCategory(StudyGroupCategory studyGroupCategory){
			this.studyGroupCategory = studyGroupCategory;
			return this;
		}

		public Builder studyGroupTitle(String studyGroupTitle){
			if (studyGroupTitle.length() > 30) {
				throw new IllegalArgumentException("제목은 30자 미만만 가능합니다.");
			} else {
				this.studyGroupTitle = studyGroupTitle;
			}
			return this;
		}

		public Builder studyGroupContent(String studyGroupContent){
			if (studyGroupContent.length() > 5000) {
				throw new IllegalArgumentException("내용은 5000자 미만만 가능합니다.");
			} else {
				this.studyGroupContent = studyGroupContent;
			}
			return this;
		}

		public Builder studyGroupStatus(PostStatus studyGroupStatus){
			this.studyGroupStatus = studyGroupStatus;
			return this;
		}

		public Builder studyGroupUserId(Integer studyGroupUserId){
			this.studyGroupUserId = studyGroupUserId;
			return this;
		}

		public StudyGroupEntity build(){
			if (studyGroupCategory == null || studyGroupTitle == null || studyGroupContent == null) {
				throw new IllegalArgumentException("모든 필드를 채워주세요.");
			}
			return new StudyGroupEntity(this);
		}
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

	public LocalDateTime getStudyGroupDeletedAt() {
		return studyGroupDeletedAt;
	}

	public void setStudyGroupDeletedAt(LocalDateTime studyGroupDeletedAt) {
		this.studyGroupDeletedAt = studyGroupDeletedAt;
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

	@PrePersist // 엔티티에 처음 저장될 때에만 동작함
	public void prePersist() {
		this.studyGroupUpdatedAt = null; // 수정칸은 비어있도록
	}

	@PreUpdate // 엔티티가 수정될때 호출됨
	public void preUpdate() {
		this.studyGroupUpdatedAt = LocalDateTime.now(); // 수정될때는 현재 시간으로 호출되게 함
	}

	@Override
	public String toString() {
		return "StudyGroupEntity{" +
			"studyGroupId=" + studyGroupId +
			", studyGroupCategory=" + studyGroupCategory +
			", studyGroupTitle='" + studyGroupTitle + '\'' +
			", studyGroupContent='" + studyGroupContent + '\'' +
			", studyGroupCreatedAt=" + studyGroupCreatedAt +
			", studyGroupUpdatedAt=" + studyGroupUpdatedAt +
			", studyGroupDeletedAt=" + studyGroupDeletedAt +
			", studyGroupStatus=" + studyGroupStatus +
			", studyGroupUserId=" + studyGroupUserId +
			'}';
	}
}
