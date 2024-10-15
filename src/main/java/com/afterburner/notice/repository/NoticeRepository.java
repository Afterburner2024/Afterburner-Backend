package com.afterburner.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.notice.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
