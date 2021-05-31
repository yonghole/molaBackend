package com.mola.mola.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode{

    INTERNAL_SERVER_ERROR(500, "C000", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method not Allowed"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    EMAIL_DUPLICATION(409, "M001", "Email is Duplicated"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
    USER_NOT_EXIST_ERROR(404,"M003", "Invalid User ID"),
    USER_ID_NULL_ERROR(405,"M004","USER_ID is null."),
    OUTSOURCE_ID_INVALID_ERROR(406,"M005","Invalid OutSource ID");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}