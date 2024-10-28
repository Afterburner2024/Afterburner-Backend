package com.afterburner.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.notice.model.Notice;
import com.afterburner.notice.model.NoticeDTO;
import com.afterburner.notice.repository.NoticeRepository;
import com.afterburner.oauth.model.User;
import com.afterburner.oauth.repository.UserRepository;

@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final UserRepository userRepository;

	@Autowired
	public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
		this.noticeRepository = noticeRepository;
		this.userRepository = userRepository;
	}

	// 유저 정보 등록해야함
	// 공지사항 등록
	public NoticeDTO createNotice(NoticeDTO noticeDTO) {



		Notice notice = new Notice();
		notice.setNoticeTitle(noticeDTO.getNoticeTitle());
		notice.setNoticeContent(noticeDTO.getNoticeContent());
		notice.setNoticeStatus(PostStatus.DEFAULT);
		notice.setNoticeCreatedAt(LocalDateTime.now());
		notice.setNoticeUpdatedAt(LocalDateTime.now());
		notice.setNoticeDeletedAt(null);
		// notice.setNoticeUserId(user);

		Notice savedNotice = noticeRepository.save(notice);

		return new NoticeDTO(
			savedNotice.getNoticeId(),
			savedNotice.getNoticeTitle(),
			savedNotice.getNoticeContent(),
			savedNotice.getNoticeStatus(),
			savedNotice.getNoticeCreatedAt(),
			savedNotice.getNoticeUpdatedAt(),
			savedNotice.getNoticeDeletedAt(),
			savedNotice.getNoticeUserId()
		);
	}

	// 전체 공지사항 조회 (삭제되지 않은 것만)
	public List<NoticeDTO> getAllNotices() {
		List<Notice> notices = noticeRepository.findAll()
			.stream()
			.filter(notice -> notice.getNoticeStatus() != PostStatus.DELETED) // DELETED가 아닌 것만 필터링
			.toList();

		return notices.stream()
			.map(notice -> new NoticeDTO(
				notice.getNoticeId(),
				notice.getNoticeTitle(),
				notice.getNoticeContent(),
				notice.getNoticeStatus(),
				notice.getNoticeCreatedAt(),
				notice.getNoticeUpdatedAt(),
				notice.getNoticeDeletedAt(),
				notice.getNoticeUserId()
			))
			.collect(Collectors.toList());
	}

	// 특정 공지사항 조회 (삭제되지 않은 것만)
	public NoticeDTO getNoticeById(Integer noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		if (notice.getNoticeStatus() == PostStatus.DELETED) {
			throw new RuntimeException("삭제된 공지사항입니다.");
		}

		return new NoticeDTO(
			notice.getNoticeId(),
			notice.getNoticeTitle(),
			notice.getNoticeContent(),
			notice.getNoticeStatus(),
			notice.getNoticeCreatedAt(),
			notice.getNoticeUpdatedAt(),
			notice.getNoticeDeletedAt(),
			notice.getNoticeUserId()
		);
	}

	// 공지사항 수정 (삭제되지 않은 것만)
	public NoticeDTO updateNotice(Integer noticeId, NoticeDTO noticeDTO) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		if (notice.getNoticeStatus() == PostStatus.DELETED) {
			throw new RuntimeException("삭제된 공지사항은 수정할 수 없습니다.");
		}

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
			updatedNotice.getNoticeDeletedAt(),
			updatedNotice.getNoticeUserId()
		);
	}

	// 공지사항 삭제
	public NoticeDTO deleteNotice(Integer noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

		notice.setNoticeStatus(PostStatus.DELETED);
		notice.setNoticeDeletedAt(LocalDateTime.now());

		Notice deletedNotice = noticeRepository.save(notice);

		return new NoticeDTO(
			deletedNotice.getNoticeId(),
			deletedNotice.getNoticeTitle(),
			deletedNotice.getNoticeContent(),
			deletedNotice.getNoticeStatus(),
			deletedNotice.getNoticeCreatedAt(),
			deletedNotice.getNoticeUpdatedAt(),
			deletedNotice.getNoticeDeletedAt(),
			deletedNotice.getNoticeUserId()
		);
	}
}

