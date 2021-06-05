package com.mola.mola.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.CriteriaBuilder;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode{

    INTERNAL_SERVER_ERROR(500, "C000", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED(405, "C002", "Method not Allowed", HttpStatus.METHOD_NOT_ALLOWED),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied", HttpStatus.FORBIDDEN),

    EMAIL_DUPLICATION(409, "M001", "Email is Duplicated", HttpStatus.CONFLICT),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST_ERROR(404,"M003", "Invalid User ID", HttpStatus.NOT_FOUND),
    USER_ID_NULL_ERROR(405,"M004","USER_ID is null.", HttpStatus.METHOD_NOT_ALLOWED),
    OUTSOURCE_ID_INVALID_ERROR(404,"M005","Invalid OutSource ID", HttpStatus.NOT_FOUND),

    IMAGE_NOT_FOUND(404,"M006","No Such Image", HttpStatus.NOT_FOUND);

    private int status;
    private String code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(int status, String code, String message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}