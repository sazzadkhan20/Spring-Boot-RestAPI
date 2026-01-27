// src/main/java/com/company/bookmanagement/model/dto/request/CreateBookRequest.java
package com.company.bookmanagement.model.dto.request;

import com.company.bookmanagement.validator.YearRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create Book Request DTO
 *
 * Data Transfer Object for creating a new book.
 * Contains validation annotations for input validation.
 *
 * VALIDATION RULES:
 * - title: Required, 1-200 characters
 * - author: Required, 1-200 characters
 * - year: Optional, 1800 to current year
 * - genre: Optional, max 100 characters
 *
 * WORKFLOW:
 * 1. Client sends JSON request body
 * 2. Spring deserializes into this DTO
 * 3. @Valid triggers bean validation
 * 4. If validation fails, MethodArgumentNotValidException is thrown
 * 5. GlobalExceptionHandler returns 400 with error details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating a new book")
public class CreateBookRequest {

    /**
     * Title of the book
     *
     * @NotBlank ensures the field is not null, empty, or whitespace-only
     * @Size validates the length is between 1 and 200 characters
     */
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(
            description = "Title of the book",
            example = "The Great Gatsby",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 200
    )
    private String title;

    /**
     * Author of the book
     */
    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 200, message = "Author must be between 1 and 200 characters")
    @Schema(
            description = "Author of the book",
            example = "F. Scott Fitzgerald",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 200
    )
    private String author;

    /**
     * Publication year of the book
     *
     * @YearRange is a custom validator that checks if the year
     * is between 1800 and the current year
     */
    @YearRange(message = "Year must be between 1800 and current year")
    @Schema(
            description = "Publication year (optional, must be between 1800 and current year)",
            example = "1925",
            minimum = "1800"
    )
    private Integer year;

    /**
     * Genre of the book
     */
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    @Schema(
            description = "Genre of the book (optional)",
            example = "Fiction",
            maxLength = 100
    )
    private String genre;
}