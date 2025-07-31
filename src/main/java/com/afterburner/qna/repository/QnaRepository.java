package com.afterburner.qna.repository;

import com.afterburner.qna.model.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {
    List<QnaEntity> findByQnaUserId(Integer userId);
}