package com.afterburner.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.notice.model.Notice;
import com.afterburner.notice.model.NoticeDTO;
import com.afterburner.notice.repository.NoticeRepository;

@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;

	@Autowired
	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	// 공지사항 등록
	public NoticeDTO createNotice(NoticeDTO noticeDTO) {
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeDTO.getNoticeTitle());
		notice.setNoticeContent(noticeDTO.getNoticeContent());
		notice.setNoticeStatus(PostStatus.DEFAULT);
		notice.setNoticeCreatedAt(LocalDateTime.now());
		notice.setNoticeUpdatedAt(LocalDateTime.now());
		notice.setNoticeDeletedAt(null);

		Notice savedNotice = noticeRepository.save(notice);

		return new NoticeDTO(
			savedNotice.getNoticeId(),
			savedNotice.getNoticeTitle(),
			savedNotice.getNoticeContent(),
			savedNotice.getNoticeStatus(),
			savedNotice.getNoticeCreatedAt(),
			savedNotice.getNoticeUpdatedAt(),
			savedNotice.getNoticeDeletedAt()
		);
	}

	// 전체 공지사항 조회
	public List<NoticeDTO> getAllNotices() {
		List<Notice> notices = noticeRepository.findAll()
			.stream()
			.filter(notice -> notice.getNoticeStatus() == PostStatus.DEFAULT)
			.collect(Collectors.toList());

		return notices.stream()
			.map(notice -> new NoticeDTO(
				notice.getNoticeId(),
				notice.getNoticeTitle(),
				notice.getNoticeContent(),
				notice.getNoticeStatus(),
				notice.getNoticeCreatedAt(),
				notice.getNoticeUpdatedAt(),
				notice.getNoticeDeletedAt()
			))
			.collect(Collectors.toList());
	}

	// 특정 공지사항 상세 조회
	public NoticeDTO getNoticeById(Integer noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		if (notice.getNoticeStatus() != PostStatus.DEFAULT) {
			throw new RuntimeException("삭제된 공지사항입니다.");
		}

		return new NoticeDTO(
			notice.getNoticeId(),
			notice.getNoticeTitle(),
			notice.getNoticeContent(),
			notice.getNoticeStatus(),
			notice.getNoticeCreatedAt(),
			notice.getNoticeUpdatedAt(),
			notice.getNoticeDeletedAt()
		);
	}

	// 공지사항 수정
	public NoticeDTO updateNotice(Integer noticeId, NoticeDTO noticeDTO) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		notice.setNoticeTitle(noticeDTO.getNoticeTitle());
		notice.setNoticeContent(noticeDTO.getNoticeContent());
		notice.setNoticeStatus(noticeDTO.getNoticeStatus());
		notice.setNoticeUpdatedAt(LocalDateTime.now());

		Notice updatedNotice = noticeRepository.save(notice);

		return new NoticeDTO(
			updatedNotice.getNoticeId(),
			updatedNotice.getNoticeTitle(),
			updatedNotice.getNoticeContent(),
			updatedNotice.getNoticeStatus(),
			updatedNotice.getNoticeCreatedAt(),
			updatedNotice.getNoticeUpdatedAt(),
			updatedNotice.getNoticeDeletedAt()
		);
	}

	// 공지사항 삭제
	public NoticeDTO deleteNotice(Integer noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		// 공지사항 상태를 삭제된 상태로 변경
		notice.setNoticeStatus(PostStatus.DELETED);
		notice.setNoticeDeletedAt(LocalDateTime.now());

		Notice deletedNotice = noticeRepository.save(notice);

		// 삭제된 공지사항 정보를 반환
		return new NoticeDTO(
			deletedNotice.getNoticeId(),
			deletedNotice.getNoticeTitle(),
			deletedNotice.getNoticeContent(),
			deletedNotice.getNoticeStatus(),
			deletedNotice.getNoticeCreatedAt(),
			deletedNotice.getNoticeUpdatedAt(),
			deletedNotice.getNoticeDeletedAt()
		);
	}
}
