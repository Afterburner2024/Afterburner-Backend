package com.afterburner.qna.controller;

import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.service.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qna")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Integer>> createQna(@Valid @RequestBody QnaDTO.Request request) {
        Integer qnaId = qnaService.createQna(request);
        ApiResponse<Integer> response = new ApiResponse.Builder<Integer>()
                .statusCode(SuccessCode.INSERT.getStatus())
                .message(SuccessCode.INSERT.getMessage())
                .result(qnaId)
                .build();
        return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(response);
    }

    @GetMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<QnaDTO.Response>> getQna(@PathVariable Integer qnaId) {
        QnaDTO.Response qnaResponse = qnaService.getQna(qnaId);
        ApiResponse<QnaDTO.Response> response = new ApiResponse.Builder<QnaDTO.Response>()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(qnaResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QnaDTO.Response>>> getAllQnas() {
        List<QnaDTO.Response> qnaList = qnaService.getAllQnas();
        ApiResponse<List<QnaDTO.Response>> response = new ApiResponse.Builder<List<QnaDTO.Response>>()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(qnaList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<Void>> updateQna(@PathVariable Integer qnaId, @Valid @RequestBody QnaDTO.Request request) {
        qnaService.updateQna(qnaId, request);
        ApiResponse<Void> response = new ApiResponse.Builder<Void>()
                .statusCode(SuccessCode.UPDATE.getStatus())
                .message(SuccessCode.UPDATE.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<Void>> deleteQna(@PathVariable Integer qnaId) {
        qnaService.deleteQna(qnaId);
        ApiResponse<Void> response = new ApiResponse.Builder<Void>()
                .statusCode(SuccessCode.DELETE.getStatus())
                .message(SuccessCode.DELETE.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
