package com.afterburner.qna.controller;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/qna")
public class QnaController {

    private final QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    // 등록
    @PostMapping
    public ResponseEntity<?> createQna(@Validated @RequestBody QnaDTO qnaDTO) {
        if (qnaDTO == null) {
            return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
                    .body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
        }

        QnaDTO createdQna = qnaService.createQna(qnaDTO);

        if (createdQna != null) {
            ApiResponse<QnaDTO> response = ApiResponse.<QnaDTO>builder()
                    .statusCode(SuccessCode.INSERT.getStatus())
                    .message(SuccessCode.INSERT.getMessage())
                    .result(createdQna)
                    .build();
            return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(response);
        } else {
            return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
                    .body(ErrorCode.INSERT_ERROR.getMessage());
        }
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> getAllQnas() {
        List<QnaDTO> qnas = qnaService.getAllQnas();

        ApiResponse<List<QnaDTO>> response = ApiResponse.<List<QnaDTO>>builder()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(qnas)
                .build();
        return ResponseEntity.ok(response);
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getQnaById(@PathVariable("id") Integer qnaId) {
        QnaDTO qna = qnaService.getQnaById(qnaId);

        if (qna != null) {
            ApiResponse<QnaDTO> response = ApiResponse.<QnaDTO>builder()
                    .statusCode(SuccessCode.SELECT.getStatus())
                    .message(SuccessCode.SELECT.getMessage())
                    .result(qna)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
                    .body(ErrorCode.NOT_FOUND_ERROR.getMessage());
        }
    }

    // 수정(작성한 유저만 수정가능)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQna(@PathVariable("id") Integer qnaId,@Validated @RequestBody QnaDTO qnaDTO) {
        if (qnaDTO == null) {
            return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
                    .body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
        }

        QnaDTO updatedQna = qnaService.updateQna(qnaId, qnaDTO);

        if (updatedQna != null) {
            ApiResponse<QnaDTO> response = ApiResponse.<QnaDTO>builder()
                    .statusCode(SuccessCode.UPDATE.getStatus())
                    .message(SuccessCode.UPDATE.getMessage())
                    .result(updatedQna)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
                    .body(ErrorCode.UPDATE_ERROR.getMessage());
        }
    }

    // 삭제(논리적 삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQna(@PathVariable("id") Integer qnaId) {
        if (qnaId == null || qnaId <= 0) {
            return ResponseEntity.status(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getStatus())
                    .body(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage());
        }

        QnaDTO deletedQna = qnaService.deleteQna(qnaId);

        if (deletedQna != null) {
            ApiResponse<QnaDTO> response = ApiResponse.<QnaDTO>builder()
                    .statusCode(SuccessCode.DELETE.getStatus())
                    .message(SuccessCode.DELETE.getMessage())
                    .result(deletedQna)
                    .build();
            return ResponseEntity.status(SuccessCode.DELETE.getStatus()).body(response);
        } else {
            return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                    .body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    // 답변 등록
    @PutMapping("/{id}/answer")
    public ResponseEntity<?> addAnswer(@PathVariable("id") Integer qnaId, @Validated @RequestBody QnaDTO qnaDTO) {
        if (qnaDTO == null) {
            return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
                    .body(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage());
        }

        QnaDTO answeredQna = qnaService.addAnswer(qnaId, qnaDTO);

        if (answeredQna != null) {
            ApiResponse<QnaDTO> response = ApiResponse.<QnaDTO>builder()
                    .statusCode(SuccessCode.UPDATE.getStatus())
                    .message(SuccessCode.UPDATE.getMessage())
                    .result(answeredQna)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
                    .body(ErrorCode.UPDATE_ERROR.getMessage());
        }
    }
}