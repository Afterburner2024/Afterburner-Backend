package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.response.ErrorResponse;
import com.afterburner.user.exception.UserEmailAlreadyExistsException;
import com.afterburner.user.exception.UserNotFoundException;
import com.afterburner.global.exception.NoticeNotFoundException;
import com.afterburner.global.exception.QnaNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(TechStackConversionException.class)
    public ResponseEntity<ErrorResponse> handleTechStackConversionException(TechStackConversionException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(TeamMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTeamMemberNotFoundException(TeamMemberNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoticeNotFoundException(NoticeNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(QnaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleQnaNotFoundException(QnaNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getErrorCode().getStatus())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ErrorCode.NOT_FOUND.getStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException e) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ErrorCode.FORBIDDEN.getStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        // 여러 유효성 검증 오류 중 첫 번째 메시지를 사용합니다.
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ErrorCode.INVALID_PARAMETER.getStatus())
                .message(errorMessage)
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception e) {
        // 예상치 못한 에러 로그를 남기는 것이 좋습니다.
        // log.error("Unhandled exception occurred", e);
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}