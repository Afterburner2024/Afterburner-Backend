package com.afterburner.notice.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoticeDTO {

	private Integer noticeId;

	@NotEmpty(message = "공지사항 제목은 필수입니다.")
	@Size(max = 50, message = "프로젝트 제목은 50자 이내여야 합니다.")
	private String noticeTitle;

	@NotEmpty(message = "공지사항 내용은 필수 입니다.")
	@Size(max = 5000, message = "공지사항 내용은 5000자 이내여야 합니다.")
	private String noticeContent;

	@NotNull(message = "공지상태는 필수입니다.")
	private PostStatus noticeStatus;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime noticeCreatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime noticeUpdatedAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime noticeDeletedAt;

	private Integer noticeUserId;

	public NoticeDTO() {
	}

	public NoticeDTO(Integer noticeId, String noticeTitle, String noticeContent, PostStatus noticeStatus,
		LocalDateTime noticeCreatedAt, LocalDateTime noticeUpdatedAt, LocalDateTime noticeDeletedAt, Integer noticeUserId) {
		this.noticeId = noticeId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeStatus = noticeStatus;
		this.noticeCreatedAt = noticeCreatedAt;
		this.noticeUpdatedAt = noticeUpdatedAt;
		this.noticeDeletedAt = noticeDeletedAt;
		this.noticeUserId = noticeUserId;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public PostStatus getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(PostStatus noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public LocalDateTime getNoticeCreatedAt() {
		return noticeCreatedAt;
	}

	public void setNoticeCreatedAt(LocalDateTime noticeCreatedAt) {
		this.noticeCreatedAt = noticeCreatedAt;
	}

	public LocalDateTime getNoticeUpdatedAt() {
		return noticeUpdatedAt;
	}

	public void setNoticeUpdatedAt(LocalDateTime noticeUpdatedAt) {
		this.noticeUpdatedAt = noticeUpdatedAt;
	}

	public LocalDateTime getNoticeDeletedAt() {
		return noticeDeletedAt;
	}

	public void setNoticeDeletedAt(LocalDateTime noticeDeletedAt) {
		this.noticeDeletedAt = noticeDeletedAt;
	}

	public Integer getNoticeUserId() {
		return noticeUserId;
	}

	public void setNoticeUserId(Integer noticeUserId) {
		this.noticeUserId = noticeUserId;
	}

	@Override
	public String toString() {
		return "NoticeDTO{" +
			"noticeId=" + noticeId +
			", noticeTitle='" + noticeTitle + '\'' +
			", noticeContent='" + noticeContent + '\'' +
			", noticeStatus=" + noticeStatus +
			", noticeCreatedAt=" + noticeCreatedAt +
			", noticeUpdatedAt=" + noticeUpdatedAt +
			", noticeDeletedAt=" + noticeDeletedAt +
			", noticeUserId=" + noticeUserId +
			'}';
	}
}
