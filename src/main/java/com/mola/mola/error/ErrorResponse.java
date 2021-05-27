package com.mola.mola.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError{
        private String field;
        private String value;
        private String reason;
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = errorCode.getStatus();
        errorResponse.message = errorCode.getMessage();
        errorResponse.code = errorCode.getCode();
        errorResponse.errors = processBindingResult(bindingResult);
        return errorResponse;
    }

    public static ErrorResponse of(ErrorCode errorCode){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = errorCode.getStatus();
        errorResponse.message = errorCode.getMessage();
        errorResponse.code = errorCode.getCode();
        errorResponse.errors = new ArrayList<>();
        return errorResponse;
    }

    public static List<FieldError> processBindingResult(BindingResult bindingResult){

        List<FieldError> errors = new ArrayList<FieldError>();
        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            FieldError fe = new FieldError();

            fe.field = fieldError.getField();

            if(fieldError.getRejectedValue() != null){
                fe.value = fieldError.getRejectedValue().toString();
            }else{
                fe.value = null;
            }

            fe.reason = fieldError.getDefaultMessage();

            errors.add(fe);
        }

        return errors;
    }


}
