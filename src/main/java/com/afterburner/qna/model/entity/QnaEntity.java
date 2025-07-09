package com.afterburner.qna.model.entity;

import com.afterburner.common.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "qna")
public class QnaEntity {

    @Id
    @Column(name = "qna_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qnaId;

    @Column(name = "qna_title", nullable = false, length = 50)
    private String qnaTitle;

    @Column(name = "qna_content", nullable = false, length = 5000)
    private String qnaContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "qna_status", nullable = false)
    private PostStatus qnaStatus;

    @Column(name = "qna_created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime qnaCreatedAt;

    @Column(name = "qna_updated_at")
    private LocalDateTime qnaUpdatedAt;

    @Column(name = "qna_deleted_at")
    private LocalDateTime qnaDeletedAt;

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "user_id")
//        private User user;

    @Builder
    public QnaEntity(Integer qnaId, String qnaTitle, String qnaContent, PostStatus qnaStatus, LocalDateTime qnaCreatedAt, LocalDateTime qnaUpdatedAt, LocalDateTime qnaDeletedAt) {
        this.qnaId = qnaId;
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
        this.qnaStatus = qnaStatus;
        this.qnaCreatedAt = qnaCreatedAt;
        this.qnaUpdatedAt = qnaUpdatedAt;
        this.qnaDeletedAt = qnaDeletedAt;
    }

    public void update(String qnaTitle, String qnaContent) {
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
        this.qnaUpdatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.qnaStatus = PostStatus.DELETED;
        this.qnaDeletedAt = LocalDateTime.now();
    }
}