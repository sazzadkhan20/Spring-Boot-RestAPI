package com.company.bookmanagement.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class ValidationException extends BaseException {
    private final Map<String, String> fieldErrors;
    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = new HashMap<>();
    }
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
        this.fieldErrors = new HashMap<>();
    }
    public ValidationException(Map<String, String> fieldErrors) {
        super(ErrorCode.VALIDATION_ERROR, "Multiple validation errors occurred");
        this.fieldErrors = fieldErrors;
    }
    public ValidationException(ErrorCode errorCode, Map<String, String> fieldErrors) {
        super(errorCode);
        this.fieldErrors = fieldErrors;
    }
}