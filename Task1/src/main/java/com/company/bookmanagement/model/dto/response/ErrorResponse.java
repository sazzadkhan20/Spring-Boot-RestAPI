package com.company.bookmanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response structure")
public class ErrorResponse {
    @Schema(description = "Timestamp when error occurred", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    @Schema(description = "HTTP status reason", example = "Not Found")
    private String error;
    @Schema(description = "Application error code", example = "BOOK-001")
    private String errorCode;
    @Schema(description = "Error message", example = "Book not found")
    private String message;
    @Schema(description = "Additional error details", example = "Book with ID 123 not found")
    private String details;
    @Schema(description = "Request path", example = "/api/books/123")
    private String path;
    @Schema(description = "Trace ID for debugging", example = "ABC12345")
    private String traceId;
    @Schema(description = "Field-level validation errors")
    private Map<String, String> fieldErrors;
}