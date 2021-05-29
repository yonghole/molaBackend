package com.mola.mola.exception;

import com.mola.mola.error.ErrorCode;

public class DuplicateFileException extends BusinessException{
    public DuplicateFileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
