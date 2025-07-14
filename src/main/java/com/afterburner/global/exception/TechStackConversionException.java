package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class TechStackConversionException extends BaseException {
    public TechStackConversionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TechStackConversionException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}