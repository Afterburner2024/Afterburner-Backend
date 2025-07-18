package com.afterburner.comment.model.dto;

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

/**
 * 댓글 DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Integer commentId;

    @NotEmpty(message = "댓글 내용은 필수입니다.")
    @Size(max = 1000, message = "댓글 내용은 1000자 이내여야 합니다.")
    private String commentContent;

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Integer commentUserId;

    private Integer studyGroupId;
    private Integer projectId;
    private Integer qnaId;
    private Integer communityId;

    private PostStatus commentStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime commentCreatedAt;
  
    private LocalDateTime commentUpdatedAt;
  
    private LocalDateTime commentDeletedAt;
}
