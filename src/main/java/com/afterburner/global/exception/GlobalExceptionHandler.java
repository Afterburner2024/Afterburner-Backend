package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.response.ErrorResponse;
import com.afterburner.user.exception.UserEmailAlreadyExistsException;
import com.afterburner.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> error(RuntimeException ex) { // RuntimeException도 BaseException을 상속받지 않는 경우를 대비
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
            .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
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

    // TeamMemberNotFoundException 처리
    @ExceptionHandler(TeamMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTeamMemberNotFoundException(TeamMemberNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ex.getErrorCode().getStatus())
            .message(ex.getMessage())
            .build();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
