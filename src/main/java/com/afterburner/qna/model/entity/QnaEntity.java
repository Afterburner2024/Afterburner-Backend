package com.afterburner.qna.model.entity;

import com.afterburner.common.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @Column(name = "qna_answer", length = 5000)
    private String qnaAnswer;

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

}