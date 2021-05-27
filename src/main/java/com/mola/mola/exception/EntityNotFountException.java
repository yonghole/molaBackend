package com.mola.mola.exception;

import com.mola.mola.error.ErrorCode;

public class EntityNotFountException extends BusinessException{

    public EntityNotFountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
