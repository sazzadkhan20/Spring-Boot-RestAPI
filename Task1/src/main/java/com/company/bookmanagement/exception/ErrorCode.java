// src/main/java/com/company/bookmanagement/exception/ErrorCode.java
package com.company.bookmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Centralized Error Codes Enumeration
 *
 * This enum defines all possible error codes, messages, and HTTP statuses
 * used throughout the application. This ensures consistency in error responses.
 *
 * BENEFITS:
 * 1. Single source of truth for all error definitions
 * 2. Easy to maintain and update error messages
 * 3. Consistent error codes across the application
 * 4. Type-safe error handling
 *
 * STRUCTURE:
 * - Error Code: Unique identifier (e.g., "BOOK-001")
 * - HTTP Status: Corresponding HTTP status code
 * - Message: Human-readable error description
 */
@Getter
public enum ErrorCode {

    // ==================== BOOK RELATED ERRORS (BOOK-XXX) ====================

    /**
     * Error when a book with the specified ID is not found in the system
     */
    BOOK_NOT_FOUND(
            "BOOK-001",
            HttpStatus.NOT_FOUND,
            "Book not found"
    ),

    /**
     * Error when trying to create a book that already exists
     */
    BOOK_ALREADY_EXISTS(
            "BOOK-002",
            HttpStatus.CONFLICT,
            "Book already exists with this ID"
    ),

    // ==================== VALIDATION ERRORS (VAL-XXX) ====================

    /**
     * Generic validation error for invalid input data
     */
    VALIDATION_ERROR(
            "VAL-001",
            HttpStatus.BAD_REQUEST,
            "Validation failed"
    ),

    /**
     * Error when title field validation fails
     */
    INVALID_TITLE(
            "VAL-002",
            HttpStatus.BAD_REQUEST,
            "Title must be between 1 and 200 characters"
    ),

    /**
     * Error when author field validation fails
     */
    INVALID_AUTHOR(
            "VAL-003",
            HttpStatus.BAD_REQUEST,
            "Author must be between 1 and 200 characters"
    ),

    /**
     * Error when year field is out of valid range
     */
    INVALID_YEAR(
            "VAL-004",
            HttpStatus.BAD_REQUEST,
            "Year must be between 1800 and current year"
    ),

    /**
     * Error when genre field exceeds maximum length
     */
    INVALID_GENRE(
            "VAL-005",
            HttpStatus.BAD_REQUEST,
            "Genre must not exceed 100 characters"
    ),

    /**
     * Error when request body is missing or malformed
     */
    INVALID_REQUEST_BODY(
            "VAL-006",
            HttpStatus.BAD_REQUEST,
            "Request body is missing or malformed"
    ),

    /**
     * Error when path parameter is invalid
     */
    INVALID_PATH_PARAMETER(
            "VAL-007",
            HttpStatus.BAD_REQUEST,
            "Invalid path parameter"
    ),

    // ==================== SYSTEM ERRORS (SYS-XXX) ====================

    /**
     * Generic internal server error
     */
    INTERNAL_SERVER_ERROR(
            "SYS-001",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred"
    ),

    /**
     * Error when requested resource type is not supported
     */
    RESOURCE_NOT_SUPPORTED(
            "SYS-002",
            HttpStatus.NOT_IMPLEMENTED,
            "Resource type not supported"
    );

    // ==================== ENUM FIELDS ====================

    /**
     * Unique error code identifier
     */
    private final String code;

    /**
     * HTTP status to return with this error
     */
    private final HttpStatus httpStatus;

    /**
     * Human-readable error message
     */
    private final String message;

    /**
     * Constructor for ErrorCode enum
     *
     * @param code       Unique error code
     * @param httpStatus HTTP status code
     * @param message    Error message
     */
    ErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}