// src/main/java/com/company/bookmanagement/exception/BaseException.java
package com.company.bookmanagement.exception;

import lombok.Getter;

/**
 * Base Exception Class
 *
 * This is the parent class for all custom exceptions in the application.
 * It encapsulates the error code and provides a consistent structure
 * for exception handling.
 *
 * DESIGN PATTERN: Template Method Pattern
 * - Defines the skeleton of exception structure
 * - Subclasses provide specific error codes
 *
 * WORKFLOW:
 * 1. Custom exception is thrown in service layer
 * 2. GlobalExceptionHandler catches it
 * 3. ErrorCode provides status code and message
 * 4. Consistent error response is returned to client
 */
@Getter
public abstract class BaseException extends RuntimeException {

    /**
     * The error code containing status, code, and message
     */
    private final ErrorCode errorCode;

    /**
     * Additional details about the error (optional)
     */
    private final String details;

    /**
     * Constructor with error code only
     *
     * @param errorCode The error code enum value
     */
    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = null;
    }

    /**
     * Constructor with error code and custom details
     *
     * @param errorCode The error code enum value
     * @param details   Additional error details
     */
    protected BaseException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage() + ": " + details);
        this.errorCode = errorCode;
        this.details = details;
    }

    /**
     * Constructor with error code and cause
     *
     * @param errorCode The error code enum value
     * @param cause     The original exception
     */
    protected BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.details = cause.getMessage();
    }
}