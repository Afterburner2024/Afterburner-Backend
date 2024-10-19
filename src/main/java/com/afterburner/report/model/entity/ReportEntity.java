package com.afterburner.report.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "report")
public class ReportEntity {

	@Id
	@Column(name = "report_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reportId;

	@Column(name = "report_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String reportTitle;

	@Column(name = "report_content", nullable = false)
	private String reportContent;

	@Column(name = "report_user_id", nullable = false) // 신고자 ID
	private Integer reportUserId;

	@Column(name = "report_status", nullable = false) // 처리 전, 처리중, 처리완료
	private ReportStatus reportStatus;

	@Column(name = "report_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime reportCreatedAt;

	@Column(name = "report_finished_at")
	private LocalDateTime reportFinishedAt;

	public ReportEntity() {
	}

	public ReportEntity(Builder builder) {
		this.reportTitle = builder.reportTitle;
		this.reportContent = builder.reportContent;
		this.reportUserId = builder.reportUserId;
		this.reportStatus = builder.reportStatus;
	}

	public static class Builder {
		private String reportTitle;
		private String reportContent;
		private Integer reportUserId;
		private ReportStatus reportStatus;

		public Builder reportTitle(String reportTitle) {
			if (reportTitle.length() > 30) {
				throw new IllegalArgumentException("제목은 30자 미만만 가능합니다.");
			} else {
				this.reportTitle = reportTitle;
			}
			return this;
		}

		public Builder reportContent(String reportContent) {
			this.reportContent = reportContent;
			return this;
		}

		public Builder reportUserId(Integer reportUserId) {
			this.reportUserId = reportUserId;
			return this;
		}

		public Builder reportStatus(ReportStatus reportStatus) {
			this.reportStatus = reportStatus;
			return this;
		}

		public ReportEntity build() {
			if (reportTitle == null || reportContent == null) {
				throw new IllegalArgumentException("모든 필드를 채워주세요.");
			}
			return new ReportEntity(this);
		}
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public Integer getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(Integer reportUserId) {
		this.reportUserId = reportUserId;
	}

	public ReportStatus getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(ReportStatus reportStatus) {
		this.reportStatus = reportStatus;
	}

	public LocalDateTime getReportCreatedAt() {
		return reportCreatedAt;
	}

	public void setReportCreatedAt(LocalDateTime reportCreatedAt) {
		this.reportCreatedAt = reportCreatedAt;
	}

	public LocalDateTime getReportFinishedAt() {
		return reportFinishedAt;
	}

	public void setReportFinishedAt(LocalDateTime reportFinishedAt) {
		this.reportFinishedAt = reportFinishedAt;
	}

	@Override
	public String toString() {
		return "ReportEntity{" +
			"reportId=" + reportId +
			", reportTitle='" + reportTitle + '\'' +
			", reportContent='" + reportContent + '\'' +
			", reportUserId=" + reportUserId +
			", reportStatus=" + reportStatus +
			", reportCreatedAt=" + reportCreatedAt +
			", reportFinishedAt=" + reportFinishedAt +
			'}';
	}
}
