package com.springjpa.project1.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User already existed"),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception"),
    USERNAME_INVALID(1003, "Username is invalid"),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),
    INVALID_KEY(1005, "Invalid message key"),
    USER_NOT_EXISTED(1006, "User not existed"),
    UNAUTHENTICATED(1007, "UNAUTHENTICATED"),

    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
