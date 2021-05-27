package com.mola.mola.exception;

import com.mola.mola.error.ErrorCode;

public class InvalidValueException extends BusinessException{

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
