package com.afterburner.comment.repository;

import com.afterburner.comment.model.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByStudyGroupId(Integer studyGroupId);
    List<CommentEntity> findByProjectId(Integer projectId);
    List<CommentEntity> findByQnaId(Integer qnaId);
    List<CommentEntity> findByCommunityId(Integer communityId);
}
