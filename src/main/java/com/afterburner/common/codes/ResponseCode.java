package com.afterburner.common.codes;

public class ResponseCode {
    private String code;
    private String message;

    public ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
