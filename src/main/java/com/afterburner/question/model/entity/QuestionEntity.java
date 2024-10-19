package com.afterburner.question.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "question")
public class QuestionEntity {

	@Id
	@Column(name = "question_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer questionId;

	@Column(name = "question_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String questionTitle;

	@Column(name = "question_content", nullable = false)
	private String questionContent;

	@Column(name = "question_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime questionCreatedAt;

	@Column(name = "question_updated_at")
	private LocalDateTime questionUpdatedAt;

	@Column(name = "question_deleted_at")
	private LocalDateTime questionDeletedAt;

	@Column(name = "question_status", nullable = false) // 기본값, 삭제상태, 신고된 상태
	private PostStatus questionStatus;

	@Column(name = "question_user_id", nullable = false) // 작성자 ID
	private Integer questionUserId;

	@Column(name = "question_img")
	private String questionImg;

	public QuestionEntity() {
	}

	public QuestionEntity(Builder builder) {
		this.questionTitle = builder.questionTitle;
		this.questionContent = builder.questionContent;
		this.questionStatus = builder.questionStatus;
		this.questionUserId = builder.questionUserId;
		this.questionImg = builder.questionImg;
	}

	public static class Builder {
		private String questionTitle;
		private String questionContent;
		private PostStatus questionStatus;
		private Integer questionUserId;
		private String questionImg;

		public Builder questionTitle(String questionTitle) {
			if (questionTitle.length() > 30) {
				throw new IllegalArgumentException("제목은 30자 미만만 가능합니다.");
			} else {
				this.questionTitle = questionTitle;
			}
			return this;
		}

		public Builder questionContent(String questionContent) {
			if (questionContent.length() > 5000) {
				throw new IllegalArgumentException("내용은 5000자 미만만 가능합니다.");
			} else {
				this.questionContent = questionContent;
			}
			return this;
		}

		public Builder questionStatus(PostStatus questionStatus) {
			this.questionStatus = questionStatus;
			return this;
		}

		public Builder questionUserId(Integer questionUserId) {
			this.questionUserId = questionUserId;
			return this;
		}

		public Builder questionImg(String questionImg) {
			this.questionImg = questionImg;
			return this;
		}

		public QuestionEntity build() {
			if (questionTitle == null || questionContent == null) {
				throw new IllegalArgumentException("모든 필드를 채워주세요.");
			}
			return new QuestionEntity(this);
		}
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public LocalDateTime getQuestionCreatedAt() {
		return questionCreatedAt;
	}

	public void setQuestionCreatedAt(LocalDateTime questionCreatedAt) {
		this.questionCreatedAt = questionCreatedAt;
	}

	public LocalDateTime getQuestionUpdatedAt() {
		return questionUpdatedAt;
	}

	public void setQuestionUpdatedAt(LocalDateTime questionUpdatedAt) {
		this.questionUpdatedAt = questionUpdatedAt;
	}

	public LocalDateTime getQuestionDeletedAt() {
		return questionDeletedAt;
	}

	public void setQuestionDeletedAt(LocalDateTime questionDeletedAt) {
		this.questionDeletedAt = questionDeletedAt;
	}

	public PostStatus getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(PostStatus questionStatus) {
		this.questionStatus = questionStatus;
	}

	public Integer getQuestionUserId() {
		return questionUserId;
	}

	public void setQuestionUserId(Integer questionUserId) {
		this.questionUserId = questionUserId;
	}

	public String getQuestionImg() {
		return questionImg;
	}

	public void setQuestionImg(String questionImg) {
		this.questionImg = questionImg;
	}

	@PrePersist // 엔티티에 처음 저장될 때에만 동작함
	public void prePersist() {
		this.questionUpdatedAt = null; // 수정칸은 비어있도록
	}

	@PreUpdate // 엔티티가 수정될때 호출됨
	public void preUpdate() {
		this.questionUpdatedAt = LocalDateTime.now(); // 수정될때는 현재 시간으로 호출되게 함
	}

	@Override
	public String toString() {
		return "QuestionEntity{" +
			"questionId=" + questionId +
			", questionTitle='" + questionTitle + '\'' +
			", questionContent='" + questionContent + '\'' +
			", questionCreatedAt=" + questionCreatedAt +
			", questionUpdatedAt=" + questionUpdatedAt +
			", questionDeletedAt=" + questionDeletedAt +
			", questionStatus=" + questionStatus +
			", questionUserId=" + questionUserId +
			", questionImg='" + questionImg + '\'' +
			'}';
	}
}
