package com.afterburner.qna.model.dto;

import com.afterburner.common.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
    private Integer qnaId;
    private String qnaTitle;
    private String qnaContent;
    private PostStatus qnaStatus;
    private LocalDateTime qnaCreatedAt;
    private LocalDateTime qnaUpdatedAt;
    private LocalDateTime qnaDeletedAt;
}