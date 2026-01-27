package com.company.bookmanagement.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String details;

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = null;
    }

    protected BaseException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage() + ": " + details);
        this.errorCode = errorCode;
        this.details = details;
    }

    protected BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.details = cause.getMessage();
    }
}