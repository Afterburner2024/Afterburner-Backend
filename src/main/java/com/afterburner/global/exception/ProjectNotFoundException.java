package com.afterburner.global.exception;

import com.afterburner.common.codes.ErrorCode;

public class ProjectNotFoundException extends BaseException {
    public ProjectNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProjectNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

	public ProjectNotFoundException(String message) {
			super(ErrorCode.valueOf(message));
	}
}