package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class TeamMemberNotFoundException extends BaseException {
    public TeamMemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TeamMemberNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
