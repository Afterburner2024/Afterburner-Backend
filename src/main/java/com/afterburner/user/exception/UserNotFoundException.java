package com.afterburner.user.exception;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.global.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
