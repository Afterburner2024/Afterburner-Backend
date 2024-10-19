package com.afterburner.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.report.model.dto.ReportDTO;
import com.afterburner.report.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@PostMapping
	public ResponseEntity<?> createReport(@Valid @RequestBody ReportDTO reportDTO) {
		if (reportDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		ReportDTO createdDTO = reportService.createPost(reportDTO);

		if (createdDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(createdDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> finishReport(@PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		ReportDTO finishedDTO = reportService.finishPost(id);

		if (finishedDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(finishedDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@GetMapping
	public List<ReportDTO> getAllReports() {
		return reportService.allReportList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();

		if (id <= 0 || id == null) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		ReportDTO findDTO = reportService.detailPost(id);

		if (findDTO != null) {
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(findDTO);
		} else {
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

}
