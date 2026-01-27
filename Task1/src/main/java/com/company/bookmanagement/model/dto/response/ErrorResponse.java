// src/main/java/com/company/bookmanagement/model/dto/response/ErrorResponse.java
package com.company.bookmanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Error Response DTO
 *
 * Standardized error response structure for all API errors.
 * Provides detailed error information for debugging and client handling.
 *
 * STRUCTURE:
 * {
 *   "timestamp": "2024-01-15T10:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "errorCode": "BOOK-001",
 *   "message": "Book not found",
 *   "details": "Book with ID 123 not found",
 *   "path": "/api/books/123",
 *   "traceId": "ABC12345",
 *   "fieldErrors": { "title": "Title is required" }
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response structure")
public class ErrorResponse {

    /**
     * When the error occurred
     */
    @Schema(description = "Timestamp when error occurred", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    /**
     * HTTP status code
     */
    @Schema(description = "HTTP status code", example = "404")
    private int status;

    /**
     * HTTP status reason phrase
     */
    @Schema(description = "HTTP status reason", example = "Not Found")
    private String error;

    /**
     * Application-specific error code
     */
    @Schema(description = "Application error code", example = "BOOK-001")
    private String errorCode;

    /**
     * Human-readable error message
     */
    @Schema(description = "Error message", example = "Book not found")
    private String message;

    /**
     * Additional error details
     */
    @Schema(description = "Additional error details", example = "Book with ID 123 not found")
    private String details;

    /**
     * The request path that caused the error
     */
    @Schema(description = "Request path", example = "/api/books/123")
    private String path;

    /**
     * Unique trace ID for error tracking
     */
    @Schema(description = "Trace ID for debugging", example = "ABC12345")
    private String traceId;

    /**
     * Field-specific validation errors
     */
    @Schema(description = "Field-level validation errors")
    private Map<String, String> fieldErrors;
}