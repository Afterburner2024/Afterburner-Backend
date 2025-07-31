package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class QnaNotFoundException extends BaseException {
    public QnaNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QnaNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
