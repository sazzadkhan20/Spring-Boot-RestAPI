// src/main/java/com/company/bookmanagement/controller/advice/GlobalExceptionHandler.java
package com.company.bookmanagement.controller.advice;

import com.company.bookmanagement.exception.BaseException;
import com.company.bookmanagement.exception.BookNotFoundException;
import com.company.bookmanagement.exception.ErrorCode;
import com.company.bookmanagement.exception.ValidationException;
import com.company.bookmanagement.model.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Global Exception Handler
 *
 * This class handles all exceptions thrown by controllers in a centralized manner.
 * It converts exceptions into consistent error responses.
 *
 * DESIGN PATTERN: Centralized Exception Handling
 *
 * BENEFITS:
 * 1. Consistent error response format across all endpoints
 * 2. Centralized logging of all errors
 * 3. Clean controller code (no try-catch blocks needed)
 * 4. Easy to modify error response structure
 *
 * WORKFLOW:
 * 1. Controller method throws exception
 * 2. Spring intercepts the exception
 * 3. Finds matching @ExceptionHandler method
 * 4. Handler creates ErrorResponse
 * 5. ResponseEntity returned to client
 *
 * EXCEPTION HIERARCHY (handled from most specific to least specific):
 * - BookNotFoundException -> 404
 * - ValidationException -> 400
 * - MethodArgumentNotValidException -> 400 (Bean Validation)
 * - HttpMessageNotReadableException -> 400 (JSON parsing)
 * - MethodArgumentTypeMismatchException -> 400 (Type conversion)
 * - BaseException -> Uses ErrorCode's HTTP status
 * - Exception -> 500 (fallback)
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles BookNotFoundException
     *
     * Triggered when: A book with specified ID doesn't exist
     * HTTP Status: 404 Not Found
     *
     * @param ex      The exception that was thrown
     * @param request The HTTP request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(
            BookNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Book not found: {}", ex.getMessage());

        ErrorResponse errorResponse = buildErrorResponse(
                ex.getErrorCode(),
                ex.getDetails(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    /**
     * Handles ValidationException
     *
     * Triggered when: Custom validation fails in service layer
     * HTTP Status: 400 Bad Request
     *
     * @param ex      The validation exception
     * @param request The HTTP request
     * @return ResponseEntity with validation error details
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        log.warn("Validation failed: {}", ex.getMessage());

        ErrorResponse errorResponse = buildErrorResponse(
                ex.getErrorCode(),
                ex.getDetails(),
                request.getRequestURI()
        );

        // Add field-specific errors if present
        if (ex.getFieldErrors() != null && !ex.getFieldErrors().isEmpty()) {
            errorResponse.setFieldErrors(ex.getFieldErrors());
        }

        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    /**
     * Handles Bean Validation errors (@Valid annotation)
     *
     * Triggered when: Request body fails Jakarta Bean Validation
     * HTTP Status: 400 Bad Request
     *
     * WORKFLOW:
     * 1. Request comes with invalid data
     * 2. @Valid annotation triggers validation
     * 3. Validation fails, this handler catches it
     * 4. Extracts all field errors
     * 5. Returns structured error response
     *
     * @param ex      The validation exception from Spring
     * @param request The HTTP request
     * @return ResponseEntity with all validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Request validation failed: {}", ex.getMessage());

        // Extract all field errors
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse errorResponse = buildErrorResponse(
                ErrorCode.VALIDATION_ERROR,
                "Request validation failed",
                request.getRequestURI()
        );
        errorResponse.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles JSON parsing errors
     *
     * Triggered when: Request body cannot be parsed as JSON
     * HTTP Status: 400 Bad Request
     *
     * @param ex      The parsing exception
     * @param request The HTTP request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn("Failed to parse request body: {}", ex.getMessage());

        ErrorResponse errorResponse = buildErrorResponse(
                ErrorCode.INVALID_REQUEST_BODY,
                "Request body is missing or contains invalid JSON",
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles type mismatch errors (e.g., string instead of number in path)
     *
     * Triggered when: Path variable or request parameter type conversion fails
     * HTTP Status: 400 Bad Request
     *
     * EXAMPLE:
     * GET /api/books/abc  (where 'abc' should be a number)
     *
     * @param ex      The type mismatch exception
     * @param request The HTTP request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        log.warn("Type mismatch: {}", ex.getMessage());

        String details = String.format(
                "Parameter '%s' should be of type '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
        );

        ErrorResponse errorResponse = buildErrorResponse(
                ErrorCode.INVALID_PATH_PARAMETER,
                details,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles unsupported HTTP methods
     *
     * Triggered when: Client uses wrong HTTP method (e.g., POST instead of GET)
     * HTTP Status: 405 Method Not Allowed
     *
     * @param ex      The exception
     * @param request The HTTP request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        log.warn("Method not supported: {} for {}", ex.getMethod(), request.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .error("Method Not Allowed")
                .errorCode("SYS-003")
                .message("HTTP method '" + ex.getMethod() + "' is not supported for this endpoint")
                .path(request.getRequestURI())
                .traceId(generateTraceId())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles all BaseException subclasses not caught by specific handlers
     *
     * @param ex      The base exception
     * @param request The HTTP request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex,
            HttpServletRequest request) {

        log.error("Application exception: {}", ex.getMessage());

        ErrorResponse errorResponse = buildErrorResponse(
                ex.getErrorCode(),
                ex.getDetails(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    /**
     * Handles all unhandled exceptions (fallback)
     *
     * Triggered when: Any unexpected exception occurs
     * HTTP Status: 500 Internal Server Error
     *
     * SECURITY NOTE: We don't expose internal error details to clients
     *
     * @param ex      The exception
     * @param request The HTTP request
     * @return ResponseEntity with generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        // Log full stack trace for debugging
        log.error("Unexpected error occurred: ", ex);

        ErrorResponse errorResponse = buildErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR,
                null,  // Don't expose internal details
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ==================== HELPER METHODS ====================

    /**
     * Builds a standardized error response
     *
     * @param errorCode The error code enum
     * @param details   Additional error details
     * @param path      The request path
     * @return Configured ErrorResponse object
     */
    private ErrorResponse buildErrorResponse(ErrorCode errorCode, String details, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().getReasonPhrase())
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .details(details)
                .path(path)
                .traceId(generateTraceId())
                .build();
    }

    /**
     * Generates a unique trace ID for error tracking
     *
     * @return UUID string for tracing
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}