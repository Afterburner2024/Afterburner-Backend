package com.afterburner.qna.model.dto;

import com.afterburner.qna.model.entity.QnaEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaDTO {

   private Integer qnaId;
   private String qnaTitle;
   private String qnaContent;
   private LocalDateTime qnaCreatedAt;
   private LocalDateTime qnaUpdatedAt;

   public static QnaDTO fromEntity(QnaEntity qnaEntity) {
       return QnaDTO.builder()
               .qnaId(qnaEntity.getQnaId())
               .qnaTitle(qnaEntity.getQnaTitle())
               .qnaContent(qnaEntity.getQnaContent())
               .qnaCreatedAt(qnaEntity.getQnaCreatedAt())
               .qnaUpdatedAt(qnaEntity.getQnaUpdatedAt())
               .build();
   }

   @Getter
   public static class Request {
       @NotEmpty(message = "QNA 제목은 필수입니다.")
       @Size(max = 50, message = "QNA 제목은 50자 이내여야 합니다.")
       private String qnaTitle;
            @NotEmpty(message = "QNA 내용은 필수입니다.")
            @Size(max = 5000, message = "QNA 내용은 5000자 이내여야 합니다.")
            private String qnaContent;

            public QnaEntity toEntity() {
                return QnaEntity.builder()
                        .qnaTitle(qnaTitle)
                        .qnaContent(qnaContent)
                        .build();
            }
   }

   @Getter
   @Builder
   public static class Response {
       private Integer qnaId;
       private String qnaTitle;
       private String qnaContent;
       private LocalDateTime qnaCreatedAt;
       private LocalDateTime qnaUpdatedAt;

       public static Response fromEntity(QnaEntity qnaEntity) {
           return Response.builder()
                   .qnaId(qnaEntity.getQnaId())
                   .qnaTitle(qnaEntity.getQnaTitle())
                   .qnaContent(qnaEntity.getQnaContent())
                   .qnaCreatedAt(qnaEntity.getQnaCreatedAt())
                   .qnaUpdatedAt(qnaEntity.getQnaUpdatedAt())
                   .build();
       }
   }
}

