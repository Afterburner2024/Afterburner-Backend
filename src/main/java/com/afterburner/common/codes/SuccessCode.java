package com.afterburner.common.codes;

public enum SuccessCode {
    SELECT(200, "200", "SELECT SUCCESS"),
    DELETE(200,"200", "DELETE SUCCESS"),
    SEND(200,"200", "SEND SUCCESS"),
    INSERT(201, "201", "INSERT SUCCESS"),
    UPDATE(204, "204", "UPDATE SUCCESS");

    private int status;
    private String code;
    private String message;

    SuccessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
