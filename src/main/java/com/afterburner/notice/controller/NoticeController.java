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
	public ResponseEntity<NoticeDTO> createNotice(@RequestBody NoticeDTO noticeDTO) {
		NoticeDTO createdNotice = noticeService.createNotice(noticeDTO);
		return ResponseEntity.ok(createdNotice);
	}

	// 전체 공지사항 조회
	@GetMapping
	public ResponseEntity<List<NoticeDTO>> getAllNotices() {
		List<NoticeDTO> notices = noticeService.getAllNotices();
		return ResponseEntity.ok(notices);
	}

	// 특정 공지사항 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<NoticeDTO> getNoticeById(@PathVariable("id") Integer noticeId) {
		NoticeDTO notice = noticeService.getNoticeById(noticeId);
		return ResponseEntity.ok(notice);
	}

	// 공지사항 수정
	@PutMapping("/{id}")
	public ResponseEntity<NoticeDTO> updateNotice(@PathVariable("id") Integer noticeId,
		@RequestBody NoticeDTO noticeDTO) {
		NoticeDTO updatedNotice = noticeService.updateNotice(noticeId, noticeDTO);
		return ResponseEntity.ok(updatedNotice);
	}

	// 공지사항 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotice(@PathVariable("id") Integer noticeId) {
		noticeService.deleteNotice(noticeId);
		return ResponseEntity.noContent().build();
	}
}
