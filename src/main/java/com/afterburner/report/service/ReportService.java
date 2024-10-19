package com.afterburner.report.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.report.model.dto.ReportDTO;
import com.afterburner.report.model.entity.ReportEntity;
import com.afterburner.report.model.entity.ReportStatus;
import com.afterburner.report.repository.ReportRepository;

import jakarta.transaction.Transactional;

@Service
public class ReportService {

	private final ReportRepository reportRepository;

	@Autowired
	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	@Transactional
	public ReportDTO createPost(ReportDTO reportDTO) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리

		ReportEntity entity = new ReportEntity.Builder()
			.reportTitle(reportDTO.getReportTitle())
			.reportContent(reportDTO.getReportContent())
			.reportStatus(reportDTO.getReportStatus())
			.reportUserId(reportDTO.getReportUserId())
			.build();
		ReportEntity savedEntity = reportRepository.save(entity);

		ReportDTO savedDTO = new ReportDTO();
		savedDTO.setReportId(savedEntity.getReportId());
		savedDTO.setReportTitle(savedEntity.getReportTitle());
		savedDTO.setReportContent(savedEntity.getReportContent());
		savedDTO.setReportUserId(savedEntity.getReportUserId());
		savedDTO.setReportStatus(savedEntity.getReportStatus());
		savedDTO.setReportCreatedAt(savedEntity.getReportCreatedAt());

		return savedDTO;
	}

	@Transactional
	public ReportDTO finishPost(Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<ReportEntity> reportEntity = reportRepository.findById(id);

		// 위에서 꺼낸 userId랑 조회한 게시글의 userId가 같은지 확인 -> 동일인이어야 삭제 가능함
		// 같으면

		if (reportEntity.isPresent()) { // 게시글 존재하면
			ReportEntity entity = reportEntity.get();
			entity.setReportStatus(ReportStatus.COMPLETED); // 처리 완료 상태로 변경
			entity.setReportFinishedAt(LocalDateTime.now()); // 처리 시간 입력
			ReportEntity savedEntity = reportRepository.save(entity);

			ReportDTO resultDTO = new ReportDTO();
			resultDTO.setReportId(savedEntity.getReportId());
			resultDTO.setReportTitle(savedEntity.getReportTitle());
			resultDTO.setReportContent(savedEntity.getReportContent());
			resultDTO.setReportUserId(savedEntity.getReportUserId());
			resultDTO.setReportStatus(savedEntity.getReportStatus());
			resultDTO.setReportCreatedAt(savedEntity.getReportCreatedAt());

			return resultDTO;
		} else {
			return null;
		}
	}

	public List<ReportDTO> allReportList() {
		List<ReportEntity> entities = reportRepository.findAll();
		List<ReportDTO> reportList = new ArrayList<>();

		if (entities.size() > 0) {
			for (ReportEntity entity : entities) {
				ReportDTO dto = new ReportDTO();
				dto.setReportId(entity.getReportId());
				dto.setReportTitle(entity.getReportTitle());
				dto.setReportContent(entity.getReportContent());
				dto.setReportUserId(entity.getReportUserId());
				dto.setReportStatus(entity.getReportStatus());
				dto.setReportCreatedAt(entity.getReportCreatedAt());
				reportList.add(dto);
			}
			return reportList;
		}
		return new ArrayList<>();
	}

	public ReportDTO detailPost(Integer id) {
		Optional<ReportEntity> findEntity = reportRepository.findById(id);
		ReportDTO detailDTO = new ReportDTO();

		if (findEntity.isPresent()) {
			detailDTO.setReportId(findEntity.get().getReportId());
			detailDTO.setReportTitle(findEntity.get().getReportTitle());
			detailDTO.setReportContent(findEntity.get().getReportContent());
			detailDTO.setReportUserId(findEntity.get().getReportUserId());
			detailDTO.setReportStatus(findEntity.get().getReportStatus());
			detailDTO.setReportCreatedAt(findEntity.get().getReportCreatedAt());
		}
		return detailDTO;
	}
}
