package com.afterburner.notice.model;

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

@Entity
@Table(name = "notice")
public class Notice {

	@Id
	@Column(name = "notice_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer noticeId;

	@Column(name = "notice_title", nullable = false, columnDefinition = "VARCHAR(50)")
	private String noticeTitle;

	@Column(name = "notice_content", nullable = false, columnDefinition = "VARCHAR(5000)")
	private String noticeContent;

	@Enumerated(EnumType.STRING)
	@Column(name = "notice_status", nullable = false)
	private PostStatus noticeStatus;

	@Column(name = "notice_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime noticeCreatedAt;

	@Column(name = "notice_updated_at")
	private LocalDateTime noticeUpdatedAt;

	@Column(name = "notice_deleted_at")
	private LocalDateTime noticeDeletedAt;

	@Column(name = "notice_user_id")
	private Integer noticeUserId;

	public Notice() {
	}

	public Notice(Integer noticeId, String noticeTitle, String noticeContent, PostStatus noticeStatus,
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

	public Integer getNoticeUserId() {
		return noticeUserId;
	}

	public void setNoticeUserId(Integer noticeUserId) {
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

	@Override
	public String toString() {
		return "Notice{" +
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
