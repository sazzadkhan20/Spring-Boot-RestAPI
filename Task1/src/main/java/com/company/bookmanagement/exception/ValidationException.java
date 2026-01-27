// src/main/java/com/company/bookmanagement/exception/ValidationException.java
package com.company.bookmanagement.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Validation Exception
 *
 * Thrown when input validation fails. This exception can contain
 * multiple field-level validation errors.
 *
 * USAGE:
 * - When request body validation fails
 * - When business rule validation fails
 *
 * EXAMPLE:
 * Map<String, String> errors = new HashMap<>();
 * errors.put("title", "Title is required");
 * errors.put("year", "Year must be between 1800 and 2024");
 * throw new ValidationException(errors);
 */
@Getter
public class ValidationException extends BaseException {

    /**
     * Map of field names to their validation error messages
     */
    private final Map<String, String> fieldErrors;

    /**
     * Constructor with single error message
     *
     * @param message The validation error message
     */
    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Constructor with specific error code
     *
     * @param errorCode Specific validation error code
     */
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Constructor with field errors map
     *
     * @param fieldErrors Map of field names to error messages
     */
    public ValidationException(Map<String, String> fieldErrors) {
        super(ErrorCode.VALIDATION_ERROR, "Multiple validation errors occurred");
        this.fieldErrors = fieldErrors;
    }

    /**
     * Constructor with error code and field errors
     *
     * @param errorCode   The error code
     * @param fieldErrors Map of field names to error messages
     */
    public ValidationException(ErrorCode errorCode, Map<String, String> fieldErrors) {
        super(errorCode);
        this.fieldErrors = fieldErrors;
    }
}