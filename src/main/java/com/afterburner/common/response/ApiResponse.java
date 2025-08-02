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
<<<<<<< HEAD

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
=======
>>>>>>> 2ccbe6d1a33986ac1da8f46fe88acf1efa9060ba
}