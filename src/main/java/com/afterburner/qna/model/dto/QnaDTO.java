package com.afterburner.qna.model.dto;

import com.afterburner.common.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDTO {
    private Integer qnaId;

    @NotEmpty(message = "qna 제목은 필수 입니다.")
    @Size(max = 50, message = "qna 제목은 50자 이내여야 합니다.")
    private String qnaTitle;

    @NotEmpty(message = "qna 내용은 필수 입니다.")
    @Size(max = 5000, message = "qna 내용은 5000자 이내여야 합니다.")
    private String qnaContent;

    @Size(max = 5000, message = "qna 답변은 5000자 이내여야 합니다.")
    private String qnaAnswer;

    @NotNull(message = "공지상태는 필수입니다.")
    private PostStatus qnaStatus;

    @NotNull(message = "유저 id는 필수입니다.")
    private Integer qnaUserId;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime qnaCreatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime qnaUpdatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime qnaDeletedAt;
}