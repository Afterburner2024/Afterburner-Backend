package com.afterburner.report.model.dto;

import java.time.LocalDateTime;

import com.afterburner.report.model.entity.ReportStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ReportDTO {

	private Integer reportId;
	private String reportTitle;
	private String reportContent;
	private Integer reportUserId;
	private ReportStatus reportStatus;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime reportCreatedAt;

	public ReportDTO() {
	}

	public ReportDTO(Integer reportId, String reportTitle, String reportContent, Integer reportUserId,
		ReportStatus reportStatus, LocalDateTime reportCreatedAt) {
		this.reportId = reportId;
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
		this.reportUserId = reportUserId;
		this.reportStatus = reportStatus;
		this.reportCreatedAt = reportCreatedAt;
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

	@Override
	public String toString() {
		return "ReportDTO{" +
			"reportId=" + reportId +
			", reportTitle='" + reportTitle + '\'' +
			", reportContent='" + reportContent + '\'' +
			", reportUserId=" + reportUserId +
			", reportStatus=" + reportStatus +
			", reportCreatedAt=" + reportCreatedAt +
			'}';
	}
}
