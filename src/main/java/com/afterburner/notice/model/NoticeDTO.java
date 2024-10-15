package com.afterburner.notice.model;

import java.time.LocalDateTime;

import com.afterburner.common.enums.PostStatus;

public class NoticeDTO {

	private Integer noticeId;
	private String noticeTitle;
	private String noticeContent;
	private PostStatus noticeStatus;
	private LocalDateTime noticeCreatedAt;
	private LocalDateTime noticeUpdatedAt;
	private LocalDateTime noticeDeletedAt;

	public NoticeDTO() {
	}

	public NoticeDTO(Integer noticeId, String noticeTitle, String noticeContent, PostStatus noticeStatus,
		LocalDateTime noticeCreatedAt, LocalDateTime noticeUpdatedAt, LocalDateTime noticeDeletedAt) {
		this.noticeId = noticeId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeStatus = noticeStatus;
		this.noticeCreatedAt = noticeCreatedAt;
		this.noticeUpdatedAt = noticeUpdatedAt;
		this.noticeDeletedAt = noticeDeletedAt;
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
			'}';
	}
}
