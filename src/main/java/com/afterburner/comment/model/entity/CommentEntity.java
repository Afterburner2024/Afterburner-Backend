package com.afterburner.comment.model.entity;

import com.afterburner.common.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "comment_content", nullable = false, length = 1000)
    private String commentContent;

    @CreationTimestamp
    @Column(name = "comment_created_at", nullable = false)
    private LocalDateTime commentCreatedAt;

    @Column(name = "comment_updated_at")
    private LocalDateTime commentUpdatedAt;

    @Column(name = "comment_deleted_at")
    private LocalDateTime commentDeletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status", nullable = false)
    private PostStatus commentStatus = PostStatus.DEFAULT;

    @Column(name = "comment_user_id", nullable = false)
    private Integer commentUserId;

    // 각 게시글 타입의 ID를 저장하여 연관관계를 관리한다.
    @Column(name = "studygroup_id")
    private Integer studyGroupId;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "qna_id")
    private Integer qnaId;

    @Column(name = "community_id")
    private Integer communityId;
}
