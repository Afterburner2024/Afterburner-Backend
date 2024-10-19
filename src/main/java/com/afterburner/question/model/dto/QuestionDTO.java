package com.afterburner.question.model.dto;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class QuestionDTO {

	private Integer questionId;
	private String questionTitle;
	private String questionContent;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime questionCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime questionUpdatedAt;
	private PostStatus questionStatus;
	private Integer questionUserId;
	private String questionImg;

	public QuestionDTO() {
	}

	public QuestionDTO(Integer questionId, String questionTitle, String questionContent, LocalDateTime questionCreatedAt, LocalDateTime questionUpdatedAt, PostStatus questionStatus, Integer questionUserId, String questionImg) {
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.questionContent = questionContent;
		this.questionCreatedAt = questionCreatedAt;
		this.questionUpdatedAt = questionUpdatedAt;
		this.questionStatus = questionStatus;
		this.questionUserId = questionUserId;
		this.questionImg = questionImg;
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

	@Override
	public String toString() {
		return "QuestionDTO{" +
				"questionId=" + questionId +
				", questionTitle='" + questionTitle + '\'' +
				", questionContent='" + questionContent + '\'' +
				", questionCreatedAt=" + questionCreatedAt +
				", questionUpdatedAt=" + questionUpdatedAt +
				", questionStatus=" + questionStatus +
				", questionUserId=" + questionUserId +
				", questionImg='" + questionImg + '\'' +
				'}';
	}
}
