package com.afterburner.common.response;

import com.afterburner.common.codes.SuccessCode;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T result;

    public ApiResponse() {
    }

    public ApiResponse(Builder<T> builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.result = builder.result;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse.Builder<T>()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
    return new ApiResponse.Builder<T>()
            .statusCode(statusCode)
            .message(message)
            .result(null)
            .build();
}

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }

    public static class Builder<T> {
        private int statusCode;
        private String message;
        private T result;

        public Builder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> result(T result) {
            this.result = result;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(this);
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + "'" +
                ", result=" + result +
                '}';
    }
}