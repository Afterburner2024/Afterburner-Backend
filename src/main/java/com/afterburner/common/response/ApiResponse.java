package com.afterburner.common.response;

import com.afterburner.common.codes.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(result)
                .build();
    }
}