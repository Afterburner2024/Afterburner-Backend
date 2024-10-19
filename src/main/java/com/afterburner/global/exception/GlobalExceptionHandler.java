package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> error() {
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ErrorCode.INSERT_ERROR.getStatus())
            .message(ErrorCode.INSERT_ERROR.getMessage())
            .build();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
            .message(ex.getMessage())
            .build();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(TechStackConversionException.class)
    public ResponseEntity<ErrorResponse> handleTechStackConversionException(TechStackConversionException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ErrorCode.CONVERSION_ERROR.getStatus())
            .message(ex.getMessage())
            .build();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // TeamMemberNotFoundException 처리
    @ExceptionHandler(TeamMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTeamMemberNotFoundException(TeamMemberNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
            .message(ex.getMessage())
            .build();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
