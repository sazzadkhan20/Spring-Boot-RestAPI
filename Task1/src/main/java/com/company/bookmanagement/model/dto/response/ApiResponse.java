// src/main/java/com/company/bookmanagement/model/dto/response/ApiResponse.java
package com.company.bookmanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generic API Response Wrapper
 *
 * A wrapper class for all API responses providing a consistent structure.
 *
 * STRUCTURE:
 * {
 *   "success": true,
 *   "message": "Operation successful",
 *   "data": { ... },
 *   "timestamp": "2024-01-15T10:30:00"
 * }
 *
 * @param <T> The type of data being returned
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Don't include null fields in JSON
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    /**
     * Indicates if the operation was successful
     */
    @Schema(description = "Indicates if the operation was successful", example = "true")
    private boolean success;

    /**
     * Human-readable message about the operation result
     */
    @Schema(description = "Message describing the result", example = "Book created successfully")
    private String message;

    /**
     * The actual data payload
     */
    @Schema(description = "The response data")
    private T data;

    /**
     * Timestamp of the response
     */
    @Schema(description = "Response timestamp")
    private LocalDateTime timestamp;

    /**
     * Factory method for successful responses with data
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Factory method for successful responses with custom message
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Factory method for successful responses without data
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}