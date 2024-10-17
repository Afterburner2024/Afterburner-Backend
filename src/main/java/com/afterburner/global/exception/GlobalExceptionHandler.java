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
}
