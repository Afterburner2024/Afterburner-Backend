package com.afterburner.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.notice.model.NoticeDTO;
import com.afterburner.notice.service.NoticeService;

@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {

	private final NoticeService noticeService;

	@Autowired
	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	// 공지사항 등록
	@PostMapping
	public ResponseEntity<?> createNotice(@RequestBody NoticeDTO noticeDTO) {
		if (noticeDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		NoticeDTO createdNotice = noticeService.createNotice(noticeDTO);

		if (createdNotice != null) {
			ApiResponse<NoticeDTO> response = new ApiResponse.Builder<NoticeDTO>()
				.statusCode(SuccessCode.INSERT.getStatus())
				.message(SuccessCode.INSERT.getMessage())
				.result(createdNotice)
				.build();
			return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(response);
		} else {
			return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
				.body(ErrorCode.INSERT_ERROR.getMessage());
		}
	}

	// 전체 공지사항 조회
	@GetMapping
	public ResponseEntity<?> getAllNotices() {
		List<NoticeDTO> notices = noticeService.getAllNotices();

		if (notices != null && !notices.isEmpty()) {
			ApiResponse<List<NoticeDTO>> response = new ApiResponse.Builder<List<NoticeDTO>>()
				.statusCode(SuccessCode.SELECT.getStatus())
				.message(SuccessCode.SELECT.getMessage())
				.result(notices)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ErrorCode.NOT_FOUND_ERROR.getMessage());
		}
	}

	// 특정 공지사항 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getNoticeById(@PathVariable("id") Integer noticeId) {
		NoticeDTO notice = noticeService.getNoticeById(noticeId);

		if (notice != null) {
			ApiResponse<NoticeDTO> response = new ApiResponse.Builder<NoticeDTO>()
				.statusCode(SuccessCode.SELECT.getStatus())
				.message(SuccessCode.SELECT.getMessage())
				.result(notice)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ErrorCode.NOT_FOUND_ERROR.getMessage());
		}
	}

	// 공지사항 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> updateNotice(@PathVariable("id") Integer noticeId, @RequestBody NoticeDTO noticeDTO) {
		if (noticeDTO == null) {
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
		}

		NoticeDTO updatedNotice = noticeService.updateNotice(noticeId, noticeDTO);

		if (updatedNotice != null) {
			ApiResponse<NoticeDTO> response = new ApiResponse.Builder<NoticeDTO>()
				.statusCode(SuccessCode.UPDATE.getStatus())
				.message(SuccessCode.UPDATE.getMessage())
				.result(updatedNotice)
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ErrorCode.UPDATE_ERROR.getMessage());
		}
	}

	// 공지사항 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotice(@PathVariable("id") Integer noticeId) {
		// 요청된 id 값이 null이거나 0 이하인 경우 유효하지 않은 요청 처리
		if (noticeId == null || noticeId <= 0) {
			return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
				.body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
		}

		// 삭제된 공지사항 객체를 반환받음
		NoticeDTO deletedNotice = noticeService.deleteNotice(noticeId);

		// 삭제 성공 시
		if (deletedNotice != null) {
			ApiResponse<NoticeDTO> response = new ApiResponse.Builder<NoticeDTO>()
				.statusCode(SuccessCode.DELETE.getStatus())
				.message(SuccessCode.DELETE.getMessage())
				.result(deletedNotice)  // 삭제된 공지사항 정보를 응답으로 전달
				.build();
			return ResponseEntity.status(SuccessCode.DELETE.getStatus()).body(response);
		} else {
			// 삭제 실패 시
			return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

}
