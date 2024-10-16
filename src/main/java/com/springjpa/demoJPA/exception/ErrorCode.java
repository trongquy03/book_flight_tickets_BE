package com.springjpa.demoJPA.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User already existed", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username is invalid",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1005, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "UNAUTHENTICATED", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009, "Invalid date of birth", HttpStatus.BAD_REQUEST),

    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message,HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
