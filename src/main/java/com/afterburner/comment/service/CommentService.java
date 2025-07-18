package com.afterburner.comment.service;

import com.afterburner.comment.model.dto.CommentDTO;
import com.afterburner.comment.model.entity.CommentEntity;
import com.afterburner.comment.repository.CommentRepository;
import com.afterburner.common.enums.PostStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 등록 - 스터디그룹
    @Transactional
    public CommentDTO createStudyGroupComment(Integer studyGroupId, CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentContent(dto.getCommentContent());
        entity.setCommentUserId(dto.getCommentUserId());
        entity.setStudyGroupId(studyGroupId);
        entity.setCommentStatus(PostStatus.DEFAULT);
        CommentEntity saved = commentRepository.save(entity);
        return toDTO(saved);
    }

    // 댓글 등록 - 사이드프로젝트
    @Transactional
    public CommentDTO createProjectComment(Integer projectId, CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentContent(dto.getCommentContent());
        entity.setCommentUserId(dto.getCommentUserId());
        entity.setProjectId(projectId);
        entity.setCommentStatus(PostStatus.DEFAULT);
        CommentEntity saved = commentRepository.save(entity);
        return toDTO(saved);
    }

    // 댓글 등록 - QnA
    @Transactional
    public CommentDTO createQnaComment(Integer qnaId, CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentContent(dto.getCommentContent());
        entity.setCommentUserId(dto.getCommentUserId());
        entity.setQnaId(qnaId);
        entity.setCommentStatus(PostStatus.DEFAULT);
        CommentEntity saved = commentRepository.save(entity);
        return toDTO(saved);
    }

    // 댓글 등록 - 커뮤니티
    @Transactional
    public CommentDTO createCommunityComment(Integer communityId, CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentContent(dto.getCommentContent());
        entity.setCommentUserId(dto.getCommentUserId());
        entity.setCommunityId(communityId);
        entity.setCommentStatus(PostStatus.DEFAULT);
        CommentEntity saved = commentRepository.save(entity);
        return toDTO(saved);
    }

    // 댓글 수정 - 작성자 본인만 가능
    @Transactional
    public CommentDTO updateComment(Integer commentId, Integer userId, String content) {

        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            return null;
        }
        CommentEntity entity = optional.get();

        // 작성자 본인 확인 (로그인 구현 후 교체)
        if (!entity.getCommentUserId().equals(userId)) {
            return null;
        }
        entity.setCommentContent(content);
        entity.setCommentUpdatedAt(LocalDateTime.now());
        CommentEntity saved = commentRepository.save(entity);
        return toDTO(saved);
    }

    // 댓글 삭제 - 작성자 또는 관리자만 가능
    @Transactional
    public CommentDTO deleteComment(Integer commentId, Integer userId, boolean isAdmin) {
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            return null;
        }
        CommentEntity entity = optional.get();
        // 권한 확인 (로그인 구현 후 교체)
        if (!entity.getCommentUserId().equals(userId) && !isAdmin) {
            return null;
        }
        CommentDTO dto = toDTO(entity);
        commentRepository.delete(entity);
        return dto;
    }

    public List<CommentDTO> getStudyGroupComments(Integer studyGroupId) {
        return commentRepository.findByStudyGroupId(studyGroupId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getProjectComments(Integer projectId) {
        return commentRepository.findByProjectId(projectId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getQnaComments(Integer qnaId) {
        return commentRepository.findByQnaId(qnaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getCommunityComments(Integer communityId) {
        return commentRepository.findByCommunityId(communityId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CommentDTO toDTO(CommentEntity entity) {
        return CommentDTO.builder()
                .commentId(entity.getCommentId())
                .commentContent(entity.getCommentContent())
                .commentCreatedAt(entity.getCommentCreatedAt())
                .commentUpdatedAt(entity.getCommentUpdatedAt())
                .commentDeletedAt(entity.getCommentDeletedAt())
                .commentStatus(entity.getCommentStatus())
                .commentUserId(entity.getCommentUserId())
                .studyGroupId(entity.getStudyGroupId())
                .projectId(entity.getProjectId())
                .qnaId(entity.getQnaId())
                .communityId(entity.getCommunityId())
                .build();
    }
}
