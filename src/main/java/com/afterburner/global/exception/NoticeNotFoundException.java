package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class NoticeNotFoundException extends BaseException {
    public NoticeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoticeNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
