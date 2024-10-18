package com.afterburner.common.codes;

public enum ErrorCode {
    /**
     * *********** global Error CodeList ****************
     * Http Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * **************************************************
     * */

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(404,"Bad Request Exception"),

    // @Reuqest Body 데이터 없는 경우
    REQUEST_BODY_MISSING_ERROR(400, "Required Request body is Missing"),

    //Request Parameter의 데이터가 존재하지 않는 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "Missing Servlet RequestParameter Exception"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400,"Invalid Type Value"),

    //I/O에러
    IO_ERROR(400,"I/O Exception"),
    // 파싱 에러
    JSON_PARSE_ERROR(400, "JsonParseException"),

    // Processing Error
    JACKSON_PROCESS_ERROR(400,"Jackson.core Excpetion"),

    //권한 없음
    FORBIDDEN_ERROR(403, "Forbidden Excpeption"),

    //서버 리소스 없음
    NOT_FOUND_ERROR(404, "Not Found Excpetion"),

    // null point Exception 발생
    NULL_POINT_ERROR(404, "null point Excpetion"),

    // @RequestBody, @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "hand Vaildation Exception"),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "Internal Server Error Exception"),

    AUTH_IS_NULL(200,  "AUTH_IS_NULL"),                // A404
    AUTH_TOKEN_FAIL(200, "AUTH_TOKEN_FAIL"),            // A405
    AUTH_TOKEN_INVALID(200, "AUTH_TOKEN_INVALID"),            // A406
    AUTH_TOKEN_NOT_MATCH(200,  "AUTH_TOKEN_NOT_MATCH"),        // A407
    AUTH_TOKEN_IS_NULL(200, "AUTH_TOKEN_IS_NULL"),        // A408

    /* ******************************* Custom Error CodeList ****************************************/

    // Business Exception Error
    BUSINESS_EXCEPTION_ERROR(200,  "Business Exception Error"),

    // Transaction Insert Error
    INSERT_ERROR(200,  "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "Update Transaction Error Exception"),

    // 변환 오류
    CONVERSION_ERROR(400, "JsonB 변환 오류 발생"),

    // Transaction Delete Error
    DELETE_ERROR(200, "Delete Transaction Error Exception"); // End


    private int status;
    private String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
