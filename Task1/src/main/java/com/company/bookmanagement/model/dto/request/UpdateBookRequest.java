// src/main/java/com/company/bookmanagement/model/dto/request/UpdateBookRequest.java
package com.company.bookmanagement.model.dto.request;

import com.company.bookmanagement.validator.YearRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update Book Request DTO
 *
 * Data Transfer Object for updating an existing book.
 * All fields are optional - only provided fields will be updated.
 *
 * PARTIAL UPDATE STRATEGY:
 * - If a field is null, it won't be updated
 * - If a field has a value, it will be validated and updated
 * - This allows PATCH-like behavior with PUT endpoint
 *
 * DIFFERENCE FROM CreateBookRequest:
 * - title and author are optional (can do partial updates)
 * - Uses @Size with min=1 instead of @NotBlank
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating an existing book")
public class UpdateBookRequest {

    /**
     * New title for the book
     * Optional - if not provided, title won't be updated
     * If provided, must be 1-200 characters
     */
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(
            description = "New title of the book (optional)",
            example = "The Great Gatsby - Revised Edition",
            minLength = 1,
            maxLength = 200
    )
    private String title;

    /**
     * New author for the book
     */
    @Size(min = 1, max = 200, message = "Author must be between 1 and 200 characters")
    @Schema(
            description = "New author of the book (optional)",
            example = "Francis Scott Fitzgerald",
            minLength = 1,
            maxLength = 200
    )
    private String author;

    /**
     * New publication year for the book
     */
    @YearRange(message = "Year must be between 1800 and current year")
    @Schema(
            description = "New publication year (optional)",
            example = "1926",
            minimum = "1800"
    )
    private Integer year;

    /**
     * New genre for the book
     */
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    @Schema(
            description = "New genre of the book (optional)",
            example = "Classic Fiction",
            maxLength = 100
    )
    private String genre;
}