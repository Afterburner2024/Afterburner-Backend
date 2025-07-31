package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class StudyGroupApplicationException extends BaseException {
    public StudyGroupApplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
