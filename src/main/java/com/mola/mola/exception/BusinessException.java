package com.mola.mola.exception;

import com.mola.mola.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
}
