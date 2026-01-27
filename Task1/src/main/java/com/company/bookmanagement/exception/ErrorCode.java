package com.company.bookmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BOOK_NOT_FOUND(
            "BOOK-001",
            HttpStatus.NOT_FOUND,
            "Book not found"
    ),
    BOOK_ALREADY_EXISTS(
            "BOOK-002",
            HttpStatus.CONFLICT,
            "Book already exists with this ID"
    ),
    VALIDATION_ERROR(
            "VAL-001",
            HttpStatus.BAD_REQUEST,
            "Validation failed"
    ),
    INVALID_TITLE(
            "VAL-002",
            HttpStatus.BAD_REQUEST,
            "Title must be between 1 and 200 characters"
    ),
    INVALID_AUTHOR(
            "VAL-003",
            HttpStatus.BAD_REQUEST,
            "Author must be between 1 and 200 characters"
    ),
    INVALID_YEAR(
            "VAL-004",
            HttpStatus.BAD_REQUEST,
            "Year must be between 1800 and current year"
    ),
    INVALID_GENRE(
            "VAL-005",
            HttpStatus.BAD_REQUEST,
            "Genre must not exceed 100 characters"
    ),
    INVALID_REQUEST_BODY(
            "VAL-006",
            HttpStatus.BAD_REQUEST,
            "Request body is missing or malformed"
    ),
    INVALID_PATH_PARAMETER(
            "VAL-007",
            HttpStatus.BAD_REQUEST,
            "Invalid path parameter"
    ),
    INTERNAL_SERVER_ERROR(
            "SYS-001",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred"
    ),
    RESOURCE_NOT_SUPPORTED(
            "SYS-002",
            HttpStatus.NOT_IMPLEMENTED,
            "Resource type not supported"
    );
    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}