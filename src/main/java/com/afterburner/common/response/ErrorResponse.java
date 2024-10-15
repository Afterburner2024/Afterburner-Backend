package com.afterburner.common.response;

public class ErrorResponse {
    private int statusCode;
    private String message;
    private String error;

    public ErrorResponse() {
    }

    public ErrorResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.error = builder.error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public static class Builder{
        private int statusCode;
        private String message;
        private String error;

        public Builder statusCode(int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder error(String error){
            this.error = error;
            return this;
        }

        public ErrorResponse build(){
            return new ErrorResponse(this);
        }
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
