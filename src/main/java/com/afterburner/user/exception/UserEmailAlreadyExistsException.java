package com.afterburner.user.exception;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.global.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailAlreadyExistsException extends BaseException {
    public UserEmailAlreadyExistsException() {
        super(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
    }
}
