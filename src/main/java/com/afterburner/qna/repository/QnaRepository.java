package com.afterburner.qna.repository;

import com.afterburner.qna.model.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {
}